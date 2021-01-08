package com.sverbank.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.NewCustomer;

public class BankMain {

	public static void main(String[] args) throws BusinessException, ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Sverbank App V1.0");
		System.out.println("---------------------------------------");
		int ch = 0;
		CustomerDAO dao = new CustomerDAOImpl();
		do {
			System.out.println("MAIN MENU");
			System.out.println("===========================");
			System.out.println("1)Log in to my account");
			System.out.println("2)New Customer");
			System.out.println("3)Employee log in");
			
			System.out.println("Please enter appropriate choice between 1-3");
			try {
				ch = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
			}

			switch (ch) {
//------------------------------------Customer-------------------------------------			
			case 1:
				System.out.println("Customer log in page");
	
				break;
			
//------------------------------------New Customer-------------------------------------	
		
			case 2:
				System.out.println("New customer registration page");

				break;
//----------------------------------------------------------------------------------				
//------------------------------------Employee--------------------------------------	
				
			case 3:
				System.out.println("Employee log in page");
				int chE = 0;
				
				do {
					System.out.println("EMPLOYEE MENU");
					System.out.println("===========================");
					System.out.println("1)Register new customer");
					System.out.println("2)View new customer request");

					System.out.println("3)EXIT");
					System.out.println("Please enter appropriate choice between 1-3");
					try {
						chE = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
					}

					switch (chE) {
					
//------------------------------------Register new customer-------------------------------------	
					case 1:
						System.out.println("__________________________");
						System.out.println("Register new customer page");
						System.out.println("__________________________");
						
						System.out.println("Enter your FIRST NAME");
						String first_name=sc.nextLine();
						System.out.println("Enter your LAST NAME");
						String last_name=sc.nextLine();
						System.out.println("Enter F or M for your gender");
						String gender=sc.nextLine();
						
						
//						System.out.println("Enter your DATE OF BIRTH");
//						sc.useDelimiter(",");
//						String dateString = sc.next();
//					    DateFormat formatter = new SimpleDateFormat("EEEE dd MMM yyyy");
//					    Date dob = formatter.parse(dateString);
//					    System.out.println(dob);
												
												
						System.out.println("Enter your SSN");
						Long ssn=sc.nextLong();
						System.out.println("Enter your PHONE number");
						Long contact=sc.nextLong();
						System.out.println("Enter your ADDRESS");
						String address=sc.nextLine();

						//Create new Customer
										
						Customer customer = new Customer( first_name, last_name,gender, new Date(), ssn, contact, address);
						
						
						try {
							if(dao.createCustomer(customer)!=0) {
								System.out.println("An account been created sucsessfully");
							}
						} catch (BusinessException e) {
							System.out.println(e.getMessage());
							
						}
						
						// Get new Customer ID by SSN
						
						Customer newCustomer = dao.getCustomerBySSN(ssn);
						System.out.println("You ID is" + newCustomer.getId());
						
						System.out.println("Enter starting balance for your account");
						Double balance=sc.nextDouble();
						
						// Generate new Account Number
						
					    long leftLimit = 1000000000L;
					    long rightLimit = 9999999999L;
					    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
					    
					 // Create new Account
						
						Account account = new Account(newCustomer.getId(),generatedLong, balance, "pending");
						
						try {
							if(dao.createAccount(account)!=0) {
								System.out.println("Account created successfully");
							}
						} catch (BusinessException e) {
							System.out.println(e.getMessage());
							
						}
						
						break;
					
//------------------------------------View new customer request-------------------------------------	
				
					case 2:
						System.out.println("View new customer request page");

						break;
						
//------------------------------------Exit-------------------------------------	
					case 3:
						System.out.println("See you soon");
						
						break;
						
					default:
						System.out.println("Invalid menu option... Kindly Retry................!!!!");
						break;
					}
				} while (chE != 4);
						
				break;
				
			default:
				System.out.println("Invalid menu option... Kindly Retry................!!!!");
				break;
			}
		} while (ch != 4);
	}
}

//		CustomerDAO dao = new CustomerDAOImpl();
//		
//		//Add customer
//		
//		Customer cust = new Customer(70040, "Eli", "Nole","M", new Date(), 9001312321L, 9001312321L, "5678 S Blue Ave");
//		
//		
//		try {
//			if(dao.createCustomer(cust)!=0) {
//				System.out.println("Customer created successfully");
//			}
//		} catch (BusinessException e) {
//			System.out.println(e.getMessage());
//			
//		}
//		
//		// Get customer by id


//		CustomerDAO dao = new CustomerDAOImpl();
//		try {
//			Customer customer= dao.getCustomerById(70032);
//			if(customer!=null) {
//				System.out.println("\nDetails of customerwith id "+customer.getId());
//				System.out.println(customer);
//			}
//		} catch (BusinessException e) {
//			System.out.println(e);
//		}}}
//		
//



