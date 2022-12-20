package ru.cheapestway.rest.models;

public class PricesOnDate {
    private String timestamp;
    private int price;

    public PricesOnDate(String timestamp, int price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
