package ru.cheapestway.rest.models;

public class CheapAirlines {
    private String airport;
    private float price;

    public CheapAirlines(String airport, float price) {
        this.airport = airport;
        this.price = price;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
