package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.in.hitch.Adapter.AddImageAdapter;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.GetUserImageModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;
import com.in.hitch.retrofit.Responsee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Add_photos extends AppCompatActivity {

    File photoFile, img_file;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    Uri img_url;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_Gallery_REQUEST_CODE = 101;
    public final String APP_TAG = "hitch";
    Button done;

    RecyclerView add_image_recycler;
    AddImageAdapter adapter;
    ProgressDialog progressDialog;
    ImageView addImage, addImageBack;


    List<GetUserImageModel.GetImageModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photos);


        getSupportActionBar().hide();

        add_image_recycler = findViewById(R.id.add_image_recycler);
        done = findViewById(R.id.done);
        init();


        getUserImage(User_Id);

        getPhotos();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void init() {

        addImage = findViewById(R.id.addImage);
        addImageBack = findViewById(R.id.addImageBack);

        progressDialog = new ProgressDialog(Activity_Add_photos.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        addImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {"Camera", "Add From Gallery", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_photos.this);
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (options[i].equals("Camera")) {
                            onLaunchCamera();
                        } else if (options[i].equals("Add From Gallery")) {
                            openMediaContent();
                        }
                    }
                });
                builder.show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(getApplicationContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_Gallery_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMediaContent();
            } else {
                Toast.makeText(getApplicationContext(), "Don't have permission to access file location", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        if (resultCode == Activity.RESULT_OK) {
            try {
                switch (requestCode) {
                    case 1034:
                        bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                        change_profile_photo.setImageBitmap(bitmap);
//                        Log.e("TAG", "onActivityResult: " + photoFile.getAbsolutePath());
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                Date());
                        img_file = new File(getApplicationContext().getCacheDir(), "IMG_" + timeStamp + ".jpg");

                        bitmap = null;

                        try {
                            if (img_url != null) {
                                bitmap = getBitmap(img_url);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();

                            try {
                                FileOutputStream fos = new FileOutputStream(img_file);
                                fos.write(bitmapdata);
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("img_file", "onClick: " + img_file);

                            add_user_image(User_Id, img_file);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter correct detail", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (data.getData() != null) {
//                            post();
                            bitmap = getBitmap(data.getData());
                            img_url = data.getData();

                            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                    Date());
                            img_file = new File(getApplicationContext().getCacheDir(), "IMG_" + timeStamp + ".jpg");

                            bitmap = null;

                            try {
                                if (img_url != null) {
                                    bitmap = getBitmap(img_url);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (bitmap != null) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                                byte[] bitmapdata = bos.toByteArray();

                                try {
                                    FileOutputStream fos = new FileOutputStream(img_file);
                                    fos.write(bitmapdata);
                                    fos.flush();
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.e("img_file", "onClick: " + img_file);

                                add_user_image(User_Id, img_file);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter correct detail", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // add image & change

    public void onLaunchCamera() {


        // create Intent to take a picture and return control to the calling application

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create a File reference for future access
            photoFile = getPhotoFileUri(photoFileName);

            img_url = Uri.fromFile(photoFile);

            Uri fileProvider = FileProvider.getUriForFile(Activity_Add_photos.this, "com.in.hitch.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    public void openMediaContent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        photoFile = getPhotoFileUri(photoFileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri tempFileUri = FileProvider.getUriForFile(getApplicationContext(),
                    "com.in.hitch.provider", // As defined in Manifest
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        } else {
            Uri tempFileUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        }

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    public Bitmap getBitmap(final Uri selectedimg) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        AssetFileDescriptor fileDescriptor = null;

        fileDescriptor =
                getContentResolver().openAssetFileDescriptor(selectedimg, "r");
        Bitmap bitmap
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
        options.inJustDecodeBounds = false;

        Bitmap original
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);
        System.gc();
        return original;
    }

    public int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if ((height > reqHeight) && (width > reqWidth)) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            inSampleSize++;
        }
        return inSampleSize;
    }

    public void getUserImage(String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.GetUserImage(Token, userId).enqueue(new Callback<GetUserImageModel>() {
            @Override
            public void onResponse(Call<GetUserImageModel> call, Response<GetUserImageModel> response) {

                GetUserImageModel getUserImageModel = response.body();
                List<GetUserImageModel.GetImageModel> models = getUserImageModel.getList();

                for (int i = 0; i < models.size(); i++) {
                    GetUserImageModel.GetImageModel imageData = models.get(i);

                    GetUserImageModel.GetImageModel model = new GetUserImageModel.GetImageModel(imageData.isId(), imageData.getImage_name());
                    data.add(model);

                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                Log.e("Flat_data", "onResponse: " + data.size());
            }

            @Override
            public void onFailure(Call<GetUserImageModel> call, Throwable t) {

            }
        });
    }

    public void getPhotos() {

        adapter = new AddImageAdapter(data, getApplicationContext(), new AddImageAdapter.Click() {
            @Override
            public void onItemClick(int position) {


            }

            @Override
            public void onAddImageClick(int position) {

                final String[] options = {"Edit", "Delete", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_photos.this);
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (options[i].equals("Edit")) {

                            Toast.makeText(getApplicationContext(), "" + data.get(position).isId(), Toast.LENGTH_SHORT).show();

                            final String[] options = {"Camera", "Add From Gallery", "Cancel"};
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_photos.this);
                            builder.setCancelable(false);
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (options[i].equals("Camera")) {
                                        onLaunchCamera();
                                    } else if (options[i].equals("Add From Gallery")) {
                                        openMediaContent();
                                    }
                                }
                            });
                            builder.show();


                        } else if (options[i].equals("Delete")) {
                            Toast.makeText(getApplicationContext(), "" + data.get(position).isId(), Toast.LENGTH_SHORT).show();

                            deleteUserImage(Token, User_Id, data.get(position).isId());
                        }
                    }
                });
                builder.show();

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        add_image_recycler.setLayoutManager(mLayoutManager);
        add_image_recycler.setAdapter(adapter);
    }

    private void add_user_image(String userId, File user_image) {

        Api call = AppConfig.getClient("https://www.hitch.notionprojects.tech/api/").create(Api.class);
        progressDialog.show();
        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), "123456789");
        RequestBody requestBody_flat_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        MultipartBody.Part payment_image = null;
        RequestBody requestBody_req_img = RequestBody.create(MediaType.parse("multipart/form-data"), user_image);
        payment_image = MultipartBody.Part.createFormData("image[]", img_file.getName(), requestBody_req_img);

        call.addImage(requestBody_token, requestBody_flat_id, payment_image).enqueue(new Callback<Responsee>() {
            @Override
            public void onResponse(Call<Responsee> call, Response<Responsee> response) {

                Responsee responsee = (Responsee) response.body();
                String s = responsee.getMessage();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Responsee> call, Throwable t) {
            }
        });
    }

    private void editImage(String token, String userId, File user_image) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        MultipartBody.Part edit_image = null;
        RequestBody requestBody_req_img = RequestBody.create(MediaType.parse("multipart/form-data"), user_image);
        edit_image = MultipartBody.Part.createFormData("image", img_file.getName(), requestBody_req_img);


        call.editImage(requestBody_token, requestBody_user_id, edit_image).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {


                CommonModel commonModel = response.body();
                Toast.makeText(getApplicationContext(), commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });


    }

    private void deleteUserImage(String token, String user_id, String image_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.deleteUserImage(token, user_id, image_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(Activity_Add_photos.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                progressDialog.dismiss();

            }
        });


    }

}