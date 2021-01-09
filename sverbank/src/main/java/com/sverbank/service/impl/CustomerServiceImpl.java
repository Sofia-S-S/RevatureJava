package com.sverbank.service.impl;

import java.util.List;

import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Transaction;
import com.sverbank.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDAO customerDAO = new CustomerDAOImpl();

	@Override
	public int createCustomer(Customer customer) throws BusinessException {
//		int x = 0;
//		if(customer.getFirst_name().matches("[a-zA-Z]{2,30}")) {
//			x=customerDAO.createCustomer(customer);
//			System.out.println("ilike yiu namr");
//		} else {
//			throw new BusinessException("Entered Name is INVALID");
//		}
		return 0;
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
	public CustomerLogin letCustomerLogin(String login, String password) throws BusinessException {
		CustomerLogin customer_login =null;
		if(login.matches("[a-zA-Z1-9]{4,12}")&& password.matches("[a-zA-Z1-9]{4,12}")) {
			System.out.println("letCustomerLogin service passed");
			customer_login =customerDAO.letCustomerLogin(login, password);
		}else {
			throw new BusinessException("Entered loging or password is INVALID");
		}
		return customer_login;
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
			}else {throw new BusinessException("You do not have enough funds for trunsaction");};
		}else {
			throw new BusinessException("Account Number " + account_number + " is INVALID or you do not have enough money for trunsaction");
		}
		return account;
	}

	@Override
	public Account getAccountByNumber(long account_number, String status) throws BusinessException {
		Account account = null;
		if(status.equals("active") && account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			account = customerDAO.getAccountByNumber(account_number, status);
		}else {
			throw new BusinessException("Account Number " + account_number + " is INVALID or you account is not active");
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
	public Account updateAccountStatus(String status, long account_number) throws BusinessException {
		Account account = null;
		if(status.equals("active") && account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			account = customerDAO.getAccountByNumber(account_number, status);
		}else if(status.equals("pending") && account_number>1000000000L && account_number<9999999999L) {
			System.out.println("getAccountByNumber service passed");
			account = customerDAO.getAccountByNumber(account_number, status);
		}else {
			throw new BusinessException("Account Number " + account_number + "os status "+status+" is INVALID ");
		}
		return account;
	}



}
