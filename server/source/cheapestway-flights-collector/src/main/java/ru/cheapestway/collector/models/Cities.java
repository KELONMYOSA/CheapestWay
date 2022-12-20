package ru.cheapestway.collector.models;

public class Cities {
    private Coordinates coordinates;
    private Name_translations name_translations;
    private Cases cases;
    private String code;
    private String name;
    private String time_zone;
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
}
