package com.sverbank.service.impl;


import java.util.List;

import com.sverbank.dao.AdministrationDAO;
import com.sverbank.dao.impl.AdministrationDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.Transaction;
import com.sverbank.service.AdministrationService;

public class AdministrationServiceImpl implements AdministrationService {
	
	private AdministrationDAO adminDAO = new AdministrationDAOImpl(); 

	@Override
	public Customer getCustomerById(int id) throws BusinessException {
		Customer customer = null;
		if (id > 1000 && id < 100000) {

			customer = adminDAO.getCustomerById(id);
		} else {

			throw new BusinessException("Customer Id " + id + " is INVALID......");
		}
		return customer;
	}

	@Override
	public Customer getCustomerBySSN(long ssn) throws BusinessException {
		Customer customer = null;
		if (ssn > 100000000 && ssn < 999999999) {

			customer = adminDAO.getCustomerBySSN(ssn);
		} else {

			throw new BusinessException("Customer Id " + ssn + " is INVALID......");
		}
		return customer;
	}	

	@Override
	public List<Customer> getAllCustomers() throws BusinessException {
		List<Customer> customersList = adminDAO.getAllCustomers();
		return customersList;
	}
	
	@Override
	public List<Transaction> getAllTransactions() throws BusinessException {
		List<Transaction> rtansactionsList = adminDAO.getAllTransactions();
		return rtansactionsList;
	}
	

	@Override
	public List<Account> getAccountsByStatus(String status) throws BusinessException {
		List<Account> accountsList = null;
		if (status.equals("active"))  {

			accountsList = adminDAO.getAccountsByStatus(status);
		} else if (status.equals("pending")){

			accountsList = adminDAO.getAccountsByStatus(status);
			} else {
				throw new BusinessException("Status " + status + " is INVALID");
		}
		return accountsList;
	}


	@Override
	public int updateAccountStatus(String status, long account_number) throws BusinessException {
		int up=0;
		if(status.equals("active") && account_number>1000000000L && account_number<9999999999L) {

			adminDAO.updateAccountStatus(status, account_number);
			up++;
		}else if(status.equals("pending") && account_number>1000000000L && account_number<9999999999L) {

			adminDAO.updateAccountStatus(status, account_number);
			up++;
		}else {
			throw new BusinessException("Account Number " + account_number + "os status "+status+" is INVALID ");
		}
		return up;
	}



}
