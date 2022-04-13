package com.in.hitch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfileModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    ProfileData profileData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    public class ProfileData {


        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("first_name")
        @Expose
        String first_name;

        @SerializedName("last_name")
        @Expose
        String last_name;

        @SerializedName("age")
        @Expose
        String age;

        @SerializedName("birthday")
        @Expose
        String birthday;


        @SerializedName("gender_id")
        @Expose
        String gender_id;

        @SerializedName("gender")
        @Expose
        String gender;

        @SerializedName("interest_id")
        @Expose
        String interest_id;

        @SerializedName("interest_name")
        @Expose
        String interest_name;

        @SerializedName("religion_id")
        @Expose
        String religion_id;

        @SerializedName("religion_name")
        @Expose
        String religion_name;

        @SerializedName("country_code")
        @Expose
        String country_code;


        @SerializedName("mobile_no")
        @Expose
        String mobile_no;


        @SerializedName("email")
        @Expose
        String email;

        @SerializedName("job_title")
        @Expose
        String job_title;

        @SerializedName("company_name")
        @Expose
        String company_name;


        @SerializedName("school_name")
        @Expose
        String school_name;

        @SerializedName("current_location")
        @Expose
        String current_location;

        @SerializedName("show_my_age")
        @Expose
        String show_my_age;

        @SerializedName("show_my_distance")
        @Expose
        String show_my_distance;

        @SerializedName("is_mobile_verfied")
        @Expose
        String is_mobile_verfied;

        @SerializedName("is_completed_profile")
        @Expose
        String is_completed_profile;

        @SerializedName("status")
        @Expose
        String status;

        @SerializedName("platform")
        @Expose
        String platform;


        @SerializedName("user_image")
        @Expose
        String user_image;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender_id() {
            return gender_id;
        }

        public void setGender_id(String gender_id) {
            this.gender_id = gender_id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getInterest_id() {
            return interest_id;
        }

        public void setInterest_id(String interest_id) {
            this.interest_id = interest_id;
        }

        public String getInterest_name() {
            return interest_name;
        }

        public void setInterest_name(String interest_name) {
            this.interest_name = interest_name;
        }

        public String getReligion_id() {
            return religion_id;
        }

        public void setReligion_id(String religion_id) {
            this.religion_id = religion_id;
        }

        public String getReligion_name() {
            return religion_name;
        }

        public void setReligion_name(String religion_name) {
            this.religion_name = religion_name;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCurrent_location() {
            return current_location;
        }

        public void setCurrent_location(String current_location) {
            this.current_location = current_location;
        }

        public String getShow_my_age() {
            return show_my_age;
        }

        public void setShow_my_age(String show_my_age) {
            this.show_my_age = show_my_age;
        }

        public String getShow_my_distance() {
            return show_my_distance;
        }

        public void setShow_my_distance(String show_my_distance) {
            this.show_my_distance = show_my_distance;
        }

        public String getIs_mobile_verfied() {
            return is_mobile_verfied;
        }

        public void setIs_mobile_verfied(String is_mobile_verfied) {
            this.is_mobile_verfied = is_mobile_verfied;
        }

        public String getIs_completed_profile() {
            return is_completed_profile;
        }

        public void setIs_completed_profile(String is_completed_profile) {
            this.is_completed_profile = is_completed_profile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }
    }
}
