package com.in.hitch.Model;

public class TopHitchesModel {

    int TopHitchesProfile;
    String TopHitchesName,TopHitchesDistance;

    public int getTopHitchesProfile() {
        return TopHitchesProfile;
    }

    public void setTopHitchesProfile(int topHitchesProfile) {
        TopHitchesProfile = topHitchesProfile;
    }

    public String getTopHitchesName() {
        return TopHitchesName;
    }

    public void setTopHitchesName(String topHitchesName) {
        TopHitchesName = topHitchesName;
    }

    public String getTopHitchesDistance() {
        return TopHitchesDistance;
    }

    public void setTopHitchesDistance(String topHitchesDistance) {
        TopHitchesDistance = topHitchesDistance;
    }

    public TopHitchesModel(int topHitchesProfile, String topHitchesName, String topHitchesDistance) {
        TopHitchesProfile = topHitchesProfile;
        TopHitchesName = topHitchesName;
        TopHitchesDistance = topHitchesDistance;
    }
}
