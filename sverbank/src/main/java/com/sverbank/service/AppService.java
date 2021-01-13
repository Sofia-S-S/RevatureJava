package com.sverbank.service;

import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Transaction;


public class AppService {
	
	CustomerDAOImpl dao;
	
	public boolean sendMoney (Transaction transfer,long account_number, double newBalance ) throws BusinessException 
	{
		dao.createTransactionTransfer(transfer, account_number, newBalance);
        System.out.println("Saved in database in Main class");
		return true;
     }
	public boolean getCustomerBySSN(long ssn) throws BusinessException
	{	
		boolean b = false;
		dao.getCustomerBySSN(ssn);
		if (ssn>100000000L && ssn<999999999L) {
			b = true;
        System.out.println("getCustomerBySSN test succseed");}
		return b;
		
	}
	public boolean processTransfer(double newBalance, long receiver_acc_num, long transaction_id, String type) throws BusinessException {
		dao.processTransfer(newBalance, receiver_acc_num, transaction_id, type);
		System.out.println("processTransfer");
		return true;
	}

}
