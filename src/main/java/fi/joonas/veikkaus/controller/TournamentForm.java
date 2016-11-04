package fi.joonas.veikkaus.controller;

import javax.validation.constraints.NotNull;


public class TournamentForm {

    @NotNull
    private String name;

    @NotNull
    private Integer year;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String toString() {
        return "Person(Name: " + this.name + ", Age: " + this.year + ")";
    }
}