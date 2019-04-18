package com.four_chopsticks.loginregister;

import android.widget.ImageView;
import android.widget.TextView;

public class ExploreSingleDataModel {

    String networkImage, networkTitle, networkDescription, networkCreateTime;

    //default constructor
    public ExploreSingleDataModel(){}

    public ExploreSingleDataModel(String networkImage,
                                  String networkTitle,
                                  String networkDescription,
                                  String networkCreateTime){

        this.networkImage = networkImage;
        this.networkTitle = networkTitle;
        this.networkDescription = networkDescription;
        this.networkCreateTime = networkCreateTime;
    }


    public void setNetworkCreateTime(String networkCreateTime) {
        this.networkCreateTime = networkCreateTime;
    }

    public void setNetworkDescription(String networkDescription) {
        this.networkDescription = networkDescription;
    }

    public void setNetworkImage(String networkImage) {
        this.networkImage = networkImage;
    }

    public void setNetworkTitle(String networkTitle) {
        this.networkTitle = networkTitle;
    }

    public String getNetworkCreateTime() {
        return networkCreateTime;
    }

    public String getNetworkDescription() {
        return networkDescription;
    }

    public String getNetworkImage() {
        return networkImage;
    }

    public String getNetworkTitle() {
        return networkTitle;
    }
}
