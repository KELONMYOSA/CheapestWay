package ru.cheapestway.collector.models;

public class Airlines {
    private Name_translations_e name_translations;
    private String code;
    private String name;
    private Boolean is_lowcost;

    public Name_translations_e getName_translations() {
        return name_translations;
    }

    public void setName_translations(Name_translations_e name_translations) {
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

    public Boolean getIs_lowcost() {
        return is_lowcost;
    }

    public void setIs_lowcost(Boolean is_lowcost) {
        this.is_lowcost = is_lowcost;
    }
}
