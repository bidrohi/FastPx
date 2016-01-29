package com.bidyut.app.fastpx.data;

import com.google.gson.annotations.SerializedName;

public class PxPhoto {
    public long id;
    public String name;
    public String description;
    @SerializedName("image_url")
    public String imageUrl;
}
