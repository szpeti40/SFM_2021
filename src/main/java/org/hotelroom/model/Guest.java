package org.hotelroom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
public class Guest {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer PostalCode;
    private String Address;//city-street-building-flour-door-etc
    private LocalDate Arrival;
    private LocalDate Leaving;
    private Integer Room_num;

    public Integer getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(Integer postalCode) {
        PostalCode = postalCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public LocalDate getArrival() {
        return Arrival;
    }

    public void setArrival(LocalDate arrival) {
        Arrival = arrival;
    }

    public LocalDate getLeaving() {
        return Leaving;
    }

    public void setLeaving(LocalDate leaving) {
        Leaving = leaving;
    }

    public Integer getRoom_num() {
        return Room_num;
    }

    public void setRoom_num(Integer room_num) {
        Room_num = room_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

}
