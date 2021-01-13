package com.sverbank.service;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;


public interface CustomerService {
	public int createCustomer(Customer customer) throws BusinessException;



	public List<Account> getAccountsById(int customer_id) throws BusinessException;

	public Account getAccountByNumber(long account_number) throws BusinessException;
//	public Account updateAccountBalance(long account_number, double newBalance) throws BusinessException;


	public int createLogin(CustomerLogin customer_login) throws BusinessException;
	

	
//	public Account updateAccountStatus(String status,long account_number) throws BusinessException; // no DAO

//	public int createTransfer(Transfer transfer)throws BusinessException;
}
