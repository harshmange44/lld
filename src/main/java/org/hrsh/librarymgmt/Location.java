package org.hrsh.librarymgmt;

import java.util.Objects;

public class Location {
    private String street;
    private String area;
    private String city;
    private String country;
    private int pinCode;

    public Location(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return pinCode == location.pinCode &&
                Objects.equals(street, location.street) &&
                Objects.equals(area, location.area) &&
                Objects.equals(city, location.city) &&
                Objects.equals(country, location.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, area, city, country, pinCode);
    }
}
