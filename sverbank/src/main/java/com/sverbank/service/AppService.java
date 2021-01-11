package com.sverbank.service;

import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;

import com.sverbank.model.Transfer;

public class AppService {
	
	CustomerDAOImpl dao;
	
	public boolean sendMoney (Transfer transfer,long account_number, double newBalance ) throws BusinessException 
	{
		dao.sendMoney(transfer, account_number, newBalance);
        System.out.println("Saved in database in Main class");
		return true;
     }
	public boolean getCustomerBySSN(long ssn) throws BusinessException
	{
		dao.getCustomerBySSN(ssn);
        System.out.println("getCustomerBySSN test succseed");
		return true;
		
	}
	public boolean approveTransfer(double newBalance, long receiver_acc_num, int transfer_id) throws BusinessException {
		dao.approveTransfer(newBalance, receiver_acc_num, transfer_id);
		System.out.println("approveTransfer");
		return true;
	}

}
