package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepo3;

	@Autowired
	CabRepository cabRepo3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Driver driver = new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);
		//When a driver is registered cab should be created
		Cab cab = new Cab();
		cab.setAvailable(true);
		cab.setPerKmRate(10);
		cab.setDriver(driver);
		//set cab for driver
		driver.setCab(cab);
		//save driver and cab will be saved automatically
		driverRepo3.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		driverRepo3.deleteById(driverId);
	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
		Driver driver = driverRepo3.findById(driverId).get();
		driver.getCab().setAvailable(false);
		driverRepo3.save(driver);
	}
}