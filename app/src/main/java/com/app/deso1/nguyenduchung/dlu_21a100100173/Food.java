package com.app.deso1.nguyenduchung.dlu_21a100100173;

public class Food {
    private String id;
    private String name;
    private double price;
    private String unit;
    private int imageResourceId;

    public Food() {
    }

    public Food(String id, String name, double price, String unit, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.imageResourceId = imageResourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
