package com.in.hitch.Model;

public class WhoLikesYouModel {

    int topHitchesProfile, topHitchesCancel, topHitchesLike;
    String topHitchesName, topHitchesDistance;

    public int getTopHitchesProfile() {
        return topHitchesProfile;
    }

    public void setTopHitchesProfile(int topHitchesProfile) {
        this.topHitchesProfile = topHitchesProfile;
    }

    public int getTopHitchesCancel() {
        return topHitchesCancel;
    }

    public void setTopHitchesCancel(int topHitchesCancel) {
        this.topHitchesCancel = topHitchesCancel;
    }

    public int getTopHitchesLike() {
        return topHitchesLike;
    }

    public void setTopHitchesLike(int topHitchesLike) {
        this.topHitchesLike = topHitchesLike;
    }

    public String getTopHitchesName() {
        return topHitchesName;
    }

    public void setTopHitchesName(String topHitchesName) {
        this.topHitchesName = topHitchesName;
    }

    public String getTopHitchesDistance() {
        return topHitchesDistance;
    }

    public void setTopHitchesDistance(String topHitchesDistance) {
        this.topHitchesDistance = topHitchesDistance;
    }

    public WhoLikesYouModel(int topHitchesProfile, int topHitchesCancel, int topHitchesLike, String topHitchesName, String topHitchesDistance) {
        this.topHitchesProfile = topHitchesProfile;
        this.topHitchesCancel = topHitchesCancel;
        this.topHitchesLike = topHitchesLike;
        this.topHitchesName = topHitchesName;
        this.topHitchesDistance = topHitchesDistance;
    }
}
