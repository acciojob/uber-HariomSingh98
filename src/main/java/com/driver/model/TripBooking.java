package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripBooking {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripBookingId;
    private String fromLocation;
    private String toLocation;
    private int distanceInKm;
    @Enumerated(value = EnumType.STRING)
    private TripStatus status;
    private int bill;

    //Mapping TripBooking-Customer
    @ManyToOne
    @JoinColumn
    private Customer customer;
    //Mapping TripBooking-Driver
    @ManyToOne
    @JoinColumn
    private Driver driver;

}