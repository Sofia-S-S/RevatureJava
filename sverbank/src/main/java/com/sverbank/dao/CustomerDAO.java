package com.sverbank.dao;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

import com.sverbank.model.Transaction;

public interface CustomerDAO {
	
	//---------------------------customerDAO---------------------------------
	public int createCustomer(Customer customer) throws BusinessException;
	public int createAccount (Account account) throws BusinessException;
	public int createLogin(CustomerLogin customer_login) throws BusinessException;

	

	public List<Account> getAccountsById(int customer_id) throws BusinessException;
	
	public Account getAccountByNumber(long account_number) throws BusinessException;


	
	//---------------------FinantionalOperetion-----------------------------------
	public int cashOperation(Transaction transaction, long account_number, double newBalance) throws BusinessException;

	public void createTransactionTransfer (Transaction transfer,long account_number, double newBalance ) throws BusinessException ;
	public void processTransfer(double newBalance,long account_number, long transaction_id, String type) throws BusinessException;
	

	public Transaction getTransactionById(long transaction_id) throws BusinessException;
	
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num)throws BusinessException;

	//------------------------Employee-------------------------------
	

	




}
