package ru.cheapestway.collector.models;

public class Data {
    private String origin;
    private String destination;
    private String origin_airport;
    private String destination_airport;
    private Integer price;
    private String airline;
    private String flight_number;
    private String departure_at;
    private Integer transfers;
    private Integer return_transfers;
    private Integer duration;
    private String link;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin_airport() {
        return origin_airport;
    }

    public void setOrigin_airport(String origin_airport) {
        this.origin_airport = origin_airport;
    }

    public String getDestination_airport() {
        return destination_airport;
    }

    public void setDestination_airport(String destination_airport) {
        this.destination_airport = destination_airport;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getDeparture_at() {
        return departure_at;
    }

    public void setDeparture_at(String departure_at) {
        this.departure_at = departure_at;
    }

    public Integer getTransfers() {
        return transfers;
    }

    public void setTransfers(Integer transfers) {
        this.transfers = transfers;
    }

    public Integer getReturn_transfers() {
        return return_transfers;
    }

    public void setReturn_transfers(Integer return_transfers) {
        this.return_transfers = return_transfers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
