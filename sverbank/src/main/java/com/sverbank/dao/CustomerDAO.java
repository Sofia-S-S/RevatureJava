package com.sverbank.dao;

import java.util.List;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

import com.sverbank.model.Transaction;

public interface CustomerDAO {
	
	//-------------------create Customer profile, Login, Apply for account ---------------------------
	public int createCustomer(Customer customer) throws BusinessException;
	public int createAccount (Account account) throws BusinessException;
	public int createLogin(CustomerLogin customer_login) throws BusinessException;

	
   //-------------------get One Account / All accounts ----------------------------------
	public List<Account> getAccountsById(int customer_id) throws BusinessException;
	
	public Account getAccountByNumber(long account_number) throws BusinessException;
	
	
	//---------------------get One transfer / all transfers (awaiting) -------------------

	public Transaction getTransactionById(long transaction_id) throws BusinessException;
	
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num)throws BusinessException;


}
