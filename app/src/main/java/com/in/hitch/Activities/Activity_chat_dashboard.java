package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.hitch.Adapter.ChatAdapter;
import com.in.hitch.Adapter.DashboardAdapter;
import com.in.hitch.Model.ChatDashboardModel;
import com.in.hitch.Model.ChatModel;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_chat_dashboard extends AppCompatActivity {


    File photoFile, img_file;
    Uri img_url;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_Gallery_REQUEST_CODE = 101;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    public final String APP_TAG = "hitch";


    ImageView img_back, userImage, replay_cancel, img_mic, img_chat_send, img_option, select_from_gallery, pik_from_camera;
    EditText edt_chat;
    TextView userName, msg_replay, msg_delete, msg_copy, replay_to, replay_to_msg;

    public String TAG = "Activity_chat_dashboard";
    RecyclerView recyclerView;
    DashboardAdapter adapter;
    String chatId, userId, user_Image, user_Name;
    List<ChatDashboardModel.ChatDashboardList> list = new ArrayList<>();
    List<ChatDashboardModel.ChatDashboardList> realTimeList = new ArrayList<>();
    LinearLayout replayLayout, msgLayout, msg_quote;
    RelativeLayout ChatLayout;
    String selected_msg_id, selected_msg, item_long_click;
    ProgressDialog progressDialog;

    Handler handler = new Handler();
    Runnable runnable;
    long delay = 1000;

    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dashboard_layout);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        chatId = intent.getStringExtra("chatId");
        userId = intent.getStringExtra("userId");
        user_Image = intent.getStringExtra("userImage");
        user_Name = intent.getStringExtra("userName");


        Log.e(TAG, "onCreate: " + userId + chatId);
        init();
        setData();
        getChatList(chatId, User_Id);
//        scheduleSendLocation();
    }

    private void init() {

        img_back = findViewById(R.id.img_back);
        edt_chat = findViewById(R.id.edt_chat);
        img_mic = findViewById(R.id.img_mic);
        img_option = findViewById(R.id.add_image);
        img_chat_send = findViewById(R.id.img_chat);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        recyclerView = findViewById(R.id.recycler);

        msgLayout = findViewById(R.id.chat_rl);
        replayLayout = findViewById(R.id.popup);
        msg_quote = findViewById(R.id.msg_quote);
        replay_to = findViewById(R.id.replay_to);
        replay_to_msg = findViewById(R.id.replay_to_msg);
        replay_cancel = findViewById(R.id.replay_cancel);

        ChatLayout = findViewById(R.id.ChatLayout);

        msg_delete = findViewById(R.id.msg_delete);
        msg_copy = findViewById(R.id.msg_copy);
        msg_replay = findViewById(R.id.msg_replay);


        progressDialog = new ProgressDialog(Activity_chat_dashboard.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        LayoutInflater layoutInflater = (LayoutInflater) Activity_chat_dashboard.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.chat_select_image_popup, null);
        popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        select_from_gallery = customView.findViewById(R.id.gallery);
        pik_from_camera = customView.findViewById(R.id.camera);


        Glide.with(getApplicationContext())
                .load(user_Image)
                .into(userImage);

        userName.setText(user_Name);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_chat_dashboard.this, Activity_My_chats.class);
                startActivity(i);
                finish();

            }
        });
//        Handler handler = new Handler();
//        Runnable input_finish_checker = new Runnable() {
//            public void run() {
//                if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
//                    // TODO: do what you need here
//                    // ............
//                    // ............
//                }
//            }
//        };

        //...


        edt_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                handler.removeCallbacks(input_finish_checker);

                if (edt_chat.getText().toString().length() == 0) {
                    img_chat_send.setVisibility(View.GONE);
                    img_mic.setVisibility(View.VISIBLE);
                } else {
                    img_chat_send.setVisibility(View.VISIBLE);
                    img_mic.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

//                if (s.length() > 0) {
//                    last_text_edit = System.currentTimeMillis();
//                    handler.postDelayed(input_finish_checker, delay);
//                    img_chat_send.setVisibility(View.VISIBLE);
//                    img_mic.setVisibility(View.GONE);
//                } else {
//                    img_chat_send.setVisibility(View.GONE);
//                    img_mic.setVisibility(View.VISIBLE);
//                }
            }
        });

        img_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edt_chat.getText().toString();

                String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);

                Log.e(TAG, "onClick: " + message);

                sendTextMessage(User_Id, userId, toServerUnicodeEncoded);


            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msgLayout.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "loadnd", Toast.LENGTH_SHORT).show();
            }
        });
        ChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msgLayout.setVisibility(View.VISIBLE);
                replayLayout.setVisibility(View.GONE);

            }
        });

        msg_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msg_quote.setVisibility(View.VISIBLE);
                replayLayout.setVisibility(View.GONE);

                replay_to_msg.setText(selected_msg);
                replay_to.setText("Replay To " + user_Name);

            }
        });
        msg_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMessage(selected_msg_id);

            }
        });
        msg_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", selected_msg);
                clipboard.setPrimaryClip(clip);
                msgLayout.setVisibility(View.VISIBLE);
                replayLayout.setVisibility(View.GONE);
                selected_msg.equals("");
            }
        });

        replay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msg_quote.setVisibility(View.GONE);
                msgLayout.setVisibility(View.VISIBLE);

            }
        });

        select_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMediaContent();

            }
        });

        pik_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();

            }
        });

        img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindow != null && popupWindow.isShowing() == true) {

                    popupWindow.dismiss();
                } else {

                    hideKeyboard(Activity_chat_dashboard.this);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setAnimationStyle(R.style.animationName);
                    popupWindow.showAtLocation(ChatLayout, Gravity.LEFT, 20, 800);

                }

            }
        });


    }

    public void getChatList(String chat_id, String user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);


        call.getChat(Token, chat_id, user_id).enqueue(new Callback<ChatDashboardModel>() {
            @Override
            public void onResponse(Call<ChatDashboardModel> call, Response<ChatDashboardModel> response) {
                ChatDashboardModel model = response.body();
                list.clear();
                Log.e("onResponse", "onResponse: " + model.getMessage());
                List<ChatDashboardModel.ChatDashboardList> data = model.getChatDataList();
                for (int i = 0; i < data.size(); i++) {

                    ChatDashboardModel.ChatDashboardList chatModelData = data.get(i);


                    ChatDashboardModel.ChatDashboardList chatItem = new ChatDashboardModel.ChatDashboardList(
                            chatModelData.getMsg_id(),
                            chatModelData.getUser_id(),
                            chatModelData.getImage(),
                            chatModelData.getUsername(),
                            chatModelData.getMessage(),
                            chatModelData.getTime(),
                            chatModelData.getType());

                    list.add(chatItem);
                    Log.e(TAG, "onResponse: " + chatItem.getMessage());

                }
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                Log.e(",c,c,c", "onResponse: " + adapter.getItemCount());


            }

            @Override
            public void onFailure(Call<ChatDashboardModel> call, Throwable t) {

            }
        });
    }


    public void sendTextMessage(String from_user_id, String to_user_id, String message) {

        Api call = AppConfig.getClient(base_url).create(Api.class);

        call.sendTextMessage(Token, from_user_id, to_user_id, message).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
//                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                edt_chat.setText("");
                getChatList(chatId, User_Id);

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    public void deleteMessage(String msgId) {
        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.deleteMessage(Token, msgId).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel model = response.body();
                msgLayout.setVisibility(View.VISIBLE);
                replayLayout.setVisibility(View.GONE);
                selected_msg_id.equals(selected_msg);
                getChatList(chatId, User_Id);

                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
    }

    public void setData() {

        adapter = new DashboardAdapter(list, getApplicationContext(), new DashboardAdapter.longClick() {
            @Override
            public void onItemClick(int position) {

                Toast.makeText(getApplicationContext(), "" + list.get(position).getMsg_id(), Toast.LENGTH_SHORT).show();

                if (msgLayout.getVisibility() == View.VISIBLE) {

                    msgLayout.setVisibility(View.GONE);
                    replayLayout.setVisibility(View.VISIBLE);
                    selected_msg_id = list.get(position).getMsg_id();
                    selected_msg = list.get(position).getMessage();

                    item_long_click = String.valueOf(list.get(position));

                    Log.e("selected_msg", "onItemClick: " + selected_msg_id);
                }
            }

            @Override
            public void onClick(int position) {


                if (list.get(position).getMsg_id().equals(selected_msg_id)) {
                    msgLayout.setVisibility(View.VISIBLE);
                    replayLayout.setVisibility(View.GONE);
                }
//                if (s.equals(item_long_click)) {
//                    msgLayout.setVisibility(View.VISIBLE);
//                    replayLayout.setVisibility(View.GONE);
//                }
                if (msg_quote.getVisibility() == View.VISIBLE) {
                    msg_quote.setVisibility(View.GONE);
                    msgLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
        super.onPause();
    }

    public void scheduleSendLocation() {

        handler.postDelayed(new Runnable() {
            public void run() {

//                getChatList2(chatId, User_Id);
//                int x = realTimeList.size();
//                int y = list.size();
//                if (x > y) {
//                    realTimeList = list;

                getChatList(chatId, User_Id);
//                }
                handler.postDelayed(this, delay);

            }

        }, delay);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        popupWindow.dismiss();


        if (resultCode == Activity.RESULT_OK) {

            try {
                switch (requestCode) {
                    case 1034:

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


//                            add_user_image(User_Id, img_file);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter correct detail", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (data.getData() != null) {
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


//                                add_user_image(User_Id, img_file);
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

            Uri fileProvider = FileProvider.getUriForFile(Activity_chat_dashboard.this, "com.in.hitch.provider", photoFile);
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


}