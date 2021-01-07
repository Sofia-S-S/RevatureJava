package com.sverbank.main;

import java.util.Date;


import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Customer;

public class BankMain {

	public static void main(String[] args) {

		CustomerDAO dao = new CustomerDAOImpl();
		
		//Add customer
		
		Customer cust = new Customer(70034, "Eric", "Nole","M", new Date(), 9001312321L, 9001312321L, "5678 S Blue Ave");
		
		
		try {
			if(dao.createCustomer(cust)!=0) {
				System.out.println("Customer created successfully");
			}
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			
		}
		
		// Get customer by id
		
		try {
			Customer customer= dao.getCustomerById(70032);
			if(customer!=null) {
				System.out.println("\nDetails of customerwith id "+customer.getId());
				System.out.println(customer);
			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
		

	}

}
