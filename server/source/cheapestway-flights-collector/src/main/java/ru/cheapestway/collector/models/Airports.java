package ru.cheapestway.collector.models;

public class Airports {
    private Coordinates coordinates;
    private Name_translations name_translations;
    private Cases cases;
    private String code;
    private String name;
    private String iata_type;
    private Boolean flightable;
    private String time_zone;
    private String city_code;
    private String country_code;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Cases cases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Name_translations getName_translations() {
        return name_translations;
    }

    public void setName_translations(Name_translations name_translations) {
        this.name_translations = name_translations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIata_type() {
        return iata_type;
    }

    public void setIata_type(String iata_type) {
        this.iata_type = iata_type;
    }

    public Boolean getFlightable() {
        return flightable;
    }

    public void setFlightable(Boolean flightable) {
        this.flightable = flightable;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
