package com.sverbank.service;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.Transaction;

public interface AdministrationService {
	
	public Customer getCustomerById(int id) throws BusinessException;
	public Customer getCustomerBySSN(long ssn) throws BusinessException;
	
	public List<Customer> getAllCustomers() throws BusinessException;
	public List<Account> getAccountsByStatus(String status) throws BusinessException;
	public List<Transaction> getAllTransactions() throws BusinessException;
	
	
	public int updateAccountStatus(String status,long account_number) throws BusinessException;

}
