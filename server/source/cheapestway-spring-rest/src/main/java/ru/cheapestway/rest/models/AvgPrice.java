package ru.cheapestway.rest.models;

public class AvgPrice {
    private int days;
    private float price;

    public AvgPrice(int days, float price) {
        this.days = days;
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
