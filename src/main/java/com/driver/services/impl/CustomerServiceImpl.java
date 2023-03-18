package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo2;

	@Autowired
	DriverRepository driverRepo2;

	@Autowired
	TripBookingRepository tripBookingRepo2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepo2.save(customer);

	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepo2.findById(customerId).get();
		customerRepo2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		Driver driver=null;
		List<Driver> driverList=driverRepo2.findAll();
		for(Driver driver1: driverList){
			if(driver1.getCab().isAvailable()==true){
				if(driver==null) driver=driver1;
				if(driver.getDriverId()>driver1.getDriverId()) driver=driver1;
			}
		}
		if(driver==null) throw new Exception("No cab available");

		Customer customer=customerRepo2.findById(customerId).get();

		TripBooking tripBooking=new TripBooking();

		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver);
		tripBooking.setTripStatus(TripStatus.CONFIRMED);


		customer.getTripBookingList().add(tripBooking);
		driver.getCab().setAvailable(false);

		driverRepo2.save(driver);
		customerRepo2.save(customer);

		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepo2.findById(tripId).get();
		tripBooking.setTripStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBookingRepo2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking= tripBookingRepo2.findById(tripId).get();
		tripBooking.setTripStatus(TripStatus.COMPLETED);
		int bill=tripBooking.getDriver().getCab().getPerKmRate()*tripBooking.getDistanceInKm();
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBooking.setBill(bill);

		tripBookingRepo2.save(tripBooking);

	}
}