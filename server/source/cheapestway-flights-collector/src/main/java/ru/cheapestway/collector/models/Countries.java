package ru.cheapestway.collector.models;

import ru.cheapestway.collector.models.Name_translations_e;

public class Countries {
    private Name_translations_e name_translations;
    private Cases cases;
    private String code;
    private String name;
    private String currency;

    public Name_translations_e getName_translations() {
        return name_translations;
    }

    public void setName_translations(Name_translations_e name_translations) {
        this.name_translations = name_translations;
    }

    public Cases cases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
