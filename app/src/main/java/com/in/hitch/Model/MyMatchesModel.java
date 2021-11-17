package com.in.hitch.Model;

public class MyMatchesModel {
    int matchImage,matchesLike;
    String matchesName,matchesDistance;

    public int getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(int matchImage) {
        this.matchImage = matchImage;
    }

    public int getMatchesLike() {
        return matchesLike;
    }

    public void setMatchesLike(int matchesLike) {
        this.matchesLike = matchesLike;
    }

    public String getMatchesName() {
        return matchesName;
    }

    public void setMatchesName(String matchesName) {
        this.matchesName = matchesName;
    }

    public String getMatchesDistance() {
        return matchesDistance;
    }

    public void setMatchesDistance(String matchesDistance) {
        this.matchesDistance = matchesDistance;
    }

    public MyMatchesModel(int matchImage, int matchesLike, String matchesName, String matchesDistance) {
        this.matchImage = matchImage;
        this.matchesLike = matchesLike;
        this.matchesName = matchesName;
        this.matchesDistance = matchesDistance;
    }
}
