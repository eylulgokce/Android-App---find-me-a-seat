package at.ac.univie.hci.findmeaseat.model.building;

import androidx.annotation.NonNull;

final public class Address {
    private final String street;
    private final String city;
    private final String zipCode;

    public Address(@NonNull String street, @NonNull String city, @NonNull String zipCode) {
        if (street.isEmpty()) throw new IllegalArgumentException("Street cannot be empty.");
        if (city.isEmpty()) throw new IllegalArgumentException("City cannot be empty.");
        if (zipCode.isEmpty()) throw new IllegalArgumentException("ZipCode cannot be empty.");
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
