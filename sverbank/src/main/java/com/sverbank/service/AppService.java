package com.sverbank.service;

import com.sverbank.exeption.BusinessException;

public interface AppService {
	
	//----------------------------------Administration---------------------------------------------------	
	
		public boolean getCustomerBySSN(long ssn) throws BusinessException;
		public boolean getCustomerById(int id) throws BusinessException; 
		public boolean getAccountsByStatus(String status) throws BusinessException; 
		public boolean updateAccountStatus(String status, long account_number) throws BusinessException; 
		
	//----------------------------------Customer---------------------------------------------------
		public boolean getAccountsById(int customer_id) throws BusinessException;
		public boolean getTransactionById(long transaction_id) throws BusinessException;
		public boolean getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException;
		
	//----------------------------------Login---------------------------------------------------
		public boolean employeeLogin(String employee_id, String password) throws BusinessException;
		public boolean letCustomerLogin(String login, String password) throws BusinessException;
		public boolean processTransfer(double newBalance, long receiver_acc_num, long transaction_id, String type) throws BusinessException;
}
