package com.sverbank.service;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

public interface CustomerService {
	public int createCustomer(Customer customer) throws BusinessException;
	public Customer getCustomerById(int id) throws BusinessException;
	public CustomerLogin letCustomerLogin(String login, String password)throws BusinessException;
	public List<Account> getAccountsById(int customer_id) throws BusinessException;

	public Account getAccountByNumber(long account_number) throws BusinessException;
	public Account updateAccountBalance(long account_number, double newBalance) throws BusinessException;
	public List<Account> getAccountsByStatus(String status) throws BusinessException;
	public int updateAccountStatus(String status,long account_number) throws BusinessException;

//	public int createTransfer(Transfer transfer)throws BusinessException;
}
