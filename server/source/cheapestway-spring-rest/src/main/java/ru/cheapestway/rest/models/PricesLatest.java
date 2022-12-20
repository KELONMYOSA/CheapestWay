package ru.cheapestway.rest.models;

public class PricesLatest {
    private String timestamp;
    private int price;
    private String departure_at;

    public PricesLatest(String timestamp, int price, String departure_at) {
        this.timestamp = timestamp;
        this.price = price;
        this.departure_at = departure_at;
    }

    public String getDeparture_at() {
        return departure_at;
    }

    public void setDeparture_at(String departure_at) {
        this.departure_at = departure_at;
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
