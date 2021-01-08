package com.sverbank.dao;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;


public interface CustomerDAO {
	public int createCustomer(Customer customer) throws BusinessException;
	public Customer getCustomerBySSN(long ssn) throws BusinessException;
	public int createAccount (Account account) throws BusinessException;
//	public Customer getCustomerById(int id) throws BusinessException;
//	public List<Customer> getAllCustomers();
}
