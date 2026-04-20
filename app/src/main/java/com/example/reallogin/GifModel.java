package com.example.reallogin;

public class GifModel {
    private String name;
    private int resId;

    public GifModel(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }
}
