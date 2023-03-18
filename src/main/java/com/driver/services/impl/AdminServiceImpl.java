package com.driver.services.impl;

import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	DriverRepository driverRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		adminRepo.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin admin=adminRepo.findById(adminId).get();
		admin.setPassword(password);
		adminRepo.save(admin);
		return admin;

	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		Admin admin=adminRepo.findById(adminId).get();
		adminRepo.delete(admin);


	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> driverList= driverRepo.findAll();
		return driverList;

	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList= customerRepo.findAll();
		return customerList;

	}

}