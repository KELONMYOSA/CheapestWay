package ru.cheapestway.rest.models;

public class FlightNumbers {
    private int flight_number;
    private String airline;
    private int price;
    private int duration;

    public FlightNumbers(int flight_number, String airline, int price, int duration) {
        this.flight_number = flight_number;
        this.airline = airline;
        this.price = price;
        this.duration = duration;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
