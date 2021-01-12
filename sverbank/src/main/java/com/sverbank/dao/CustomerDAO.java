package com.sverbank.dao;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

import com.sverbank.model.Transaction;

public interface CustomerDAO {
	public int createCustomer(Customer customer) throws BusinessException;
	public Customer getCustomerBySSN(long ssn) throws BusinessException;
	public int createAccount (Account account) throws BusinessException;
	public int createLogin(CustomerLogin customer_login) throws BusinessException;

	

	public Customer getCustomerById(int id) throws BusinessException;
	public List<Account> getAccountsById(int customer_id) throws BusinessException;

	public Account getAccountByNumber(long account_number) throws BusinessException;
	public Account updateAccountBalance(long account_number, double newBalance) throws BusinessException;
	
	public List<Account> getAccountsByStatus(String status) throws BusinessException;
	public int updateAccountStatus(String status,long account_number) throws BusinessException;
	public int createTransaction(Transaction transaction)throws BusinessException;
	public void createTransactionTransfer (Transaction transfer,long account_number, double newBalance ) throws BusinessException ;
	public void approveTransfer(double newBalance, long receiver_acc_num, long transaction_id, String type) throws BusinessException;
	
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num)throws BusinessException;
	public Transaction getTransactionById(long transaction_id) throws BusinessException;
	

	

//	public List<Customer> getAllCustomers();
}
