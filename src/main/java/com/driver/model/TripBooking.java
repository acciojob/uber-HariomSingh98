package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public  class TripBooking{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripBookingId;

    private String fromLocation;
    private String toLocation;
    private int bill;
    private int distanceInKm;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    @ManyToOne
    @JoinColumn
    Driver driver;
    @ManyToOne
    @JoinColumn
    Customer customer;

}