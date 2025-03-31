package com.example.mobilevirtuallab;

public class Model {
    private String name;
    private String info;
    private int image;

    Model(String name, String info, int image) {
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public String getName() { return this.name; }
    public String getInfo() { return this.info; }
    public int getImage() { return this.image; }
}
