package com.sverbank.dao;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.Transaction;

public interface AdministrationDAO {
	
	//-------------------------find customer--------------------------
	public Customer getCustomerById(int id) throws BusinessException;
	public Customer getCustomerBySSN(long ssn) throws BusinessException;
	
	//--------------------------Lists---------------------------------
	public List<Customer> getAllCustomers() throws BusinessException;
	public List<Account> getAccountsByStatus(String status) throws BusinessException;
	public List<Transaction> getAllTransactions() throws BusinessException;
	
	//-------------------------New Account Validation--------------------------
	public int updateAccountStatus(String status,long account_number) throws BusinessException;

}
