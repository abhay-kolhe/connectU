package com.four_chopsticks.loginregister;

public class UploadNetwork {

    public String genre;
    public String networkTitle;
    public String descriptions;
    public String coverPhotoUrl;
    public String createdTime;

   public UploadNetwork(){

   }

   public UploadNetwork(String genre , String networkTitle , String descriptions
           , String coverPhotoUrl,String createdTime){
       this.genre = genre;
       this.coverPhotoUrl = coverPhotoUrl;
       this.descriptions = descriptions;
       this.networkTitle = networkTitle;
       this.createdTime = createdTime;
   }

    public void setNetworkTitle(String networkTitle) {
        this.networkTitle = networkTitle;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNetworkTitle() {
        return networkTitle;
    }

    public String getGenre() {
        return genre;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
