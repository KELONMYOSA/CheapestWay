package ru.cheapestway.rest.models;

public class City {
    private String code;
    private String name;
    private String time_zone;
    private String country_code;
    private String en;
    private Float lon;
    private Float lat;

    public City() {
        this.code = null;
        this.name = null;
        this.time_zone = null;
        this.country_code = null;
        this.en = null;
        this.lon = null;
        this.lat = null;
    }

    public City(String code, String name, String time_zone, String country_code, String en, Float lon, Float lat) {
        this.code = code;
        this.name = name;
        this.time_zone = time_zone;
        this.country_code = country_code;
        this.en = en;
        this.lon = lon;
        this.lat = lat;
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

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
}
