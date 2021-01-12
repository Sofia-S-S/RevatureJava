package com.sverbank.service.impl;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.main.BankMain;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;
import com.sverbank.service.CustomerService;


public class CustomerServiceImpl implements CustomerService{
	
	Scanner sc = new Scanner(System.in);
	
	private static Logger log=Logger.getLogger(BankMain.class);  // Set up log
	
	private CustomerDAO customerDAO = new CustomerDAOImpl();

	@Override
	public int createCustomer(Customer customer) throws BusinessException {
		int x = 0;
		if(customer.getFirst_name().matches("[a-zA-Z]{2,30}")) {
			if(customer.getLast_name().matches("[a-zA-Z]{2,30}")) {
				if(customer.getGender().matches("[MF]{1}")) {
					if(customer.getSsn()>100000000L && customer.getSsn()<999999999L) {
						if(customer.getAddress()!=null) {
							if(customer.getContact()>1000000000L && customer.getSsn()<9999999999L) {
							x=customerDAO.createCustomer(customer);
							} else {
								throw new BusinessException("Phone number "+customer.getContact()+" is INVALID");
							}
						} else {
							throw new BusinessException("Address "+customer.getAddress()+" is INVALID");
						}
					} else {
						throw new BusinessException("Social Security Number "+customer.getSsn()+" is INVALID");
					}
				} else {
					throw new BusinessException("Entered Gender "+customer.getGender()+" is INVALID");

				}
			} else {
				throw new BusinessException("Entered Last Name "+customer.getLast_name()+" is INVALID");
			}
		} else {
			throw new BusinessException("Entered First Name "+customer.getFirst_name()+" is INVALID");
		}
		return x;
	}

	@Override
	public Customer getCustomerById(int id) throws BusinessException {
		Customer customer = null;
		if (id > 1000 && id < 100000) {
			System.out.println("getCustomerById service passed");
			customer = customerDAO.getCustomerById(id);
		} else {
			System.out.println("getCustomerById service failed");
			throw new BusinessException("Customer Id " + id + " is INVALID......");
		}
		return customer;
	}
	
	@Override
	public int createLogin(CustomerLogin customer_login) throws BusinessException {
		int x = 0;
		if(customer_login.getLogin().matches("[a-zA-Z0-9]{4,12})") && customer_login.getPassword().matches("[a-zA-Z0-9]{4,12})")) {
			x = customerDAO.createLogin(customer_login);
		} else {
			throw new BusinessException("Login or password is INVALID. Please call bank to complite registration");
		}
		return x;
	}



	@Override
	public List<Account> getAccountsById(int customer_id) throws BusinessException {
		List<Account> accountsList = null;
		if (customer_id > 1000 && customer_id < 100000) {
			System.out.println("getAccountsById service passed");
			accountsList = customerDAO.getAccountsById(customer_id);
		} else {
			throw new BusinessException("Customer Id " + customer_id + " is INVALID......");
		}
		return accountsList;
	}

	@Override
	public Account updateAccountBalance(long account_number, double newBalance) throws BusinessException {
		Account account = null;
		if(account_number>1000000000L && account_number<9999999999L) {
			if(newBalance>0) {
			System.out.println("updateAccountBalance service passed");
			account = customerDAO.updateAccountBalance(account_number, newBalance);
			}else {throw new BusinessException("You do not have enough funds for trunsfer");};
		}else {
			throw new BusinessException("Account Number " + account_number + " is INVALID or you do not have enough money for trunsfer");
		}
		return account;
	}


	
	@Override
	public Account getAccountByNumber(long account_number) throws BusinessException {
		Account account = null;
		if(account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			account = customerDAO.getAccountByNumber(account_number);
		}else {
			throw new BusinessException("Account Number " + account_number + " is INVALID");
		}
		return account;
	}
	
	@Override
	public List<Account> getAccountsByStatus(String status) throws BusinessException {
		List<Account> accountsList = null;
		if (status.equals("active"))  {
			System.out.println("getAccountsById service passed");
			accountsList = customerDAO.getAccountsByStatus(status);
		} else if (status.equals("pending")){
			System.out.println("getAccountsById service passed");
			accountsList = customerDAO.getAccountsByStatus(status);
			} else {
				throw new BusinessException("Status " + status + " is INVALID");
		}
		return accountsList;
	}

	@Override
	public int updateAccountStatus(String status, long account_number) throws BusinessException {
		int up=0;
		if(status.equals("active") && account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			customerDAO.updateAccountStatus(status, account_number);
			up++;
		}else if(status.equals("pending") && account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			customerDAO.updateAccountStatus(status, account_number);
			up++;
		}else {
			throw new BusinessException("Account Number " + account_number + "os status "+status+" is INVALID ");
		}
		return up;
	}







}
