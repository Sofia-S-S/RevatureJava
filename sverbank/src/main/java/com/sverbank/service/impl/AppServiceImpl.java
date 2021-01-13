package com.sverbank.service.impl;


import com.sverbank.dao.impl.AdministrationDAOImpl;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.dao.impl.LoginDAOImpl;
import com.sverbank.dao.impl.TransactionDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.service.AppService;

public class AppServiceImpl implements AppService {
	
	
	AdministrationDAOImpl adminDAO;
	CustomerDAOImpl custDAO;
	LoginDAOImpl logDAO;
	TransactionDAOImpl transDAO;

//----------------------------------Administration---------------------------------------------------	
	@Override
	public boolean getCustomerBySSN(long ssn) throws BusinessException {	
		boolean b = false;
		adminDAO.getCustomerBySSN(ssn);
		if (ssn>100000000L && ssn<999999999L) {
			b = true;
      }
		return b;
	}
	
	@Override
	public boolean getCustomerById(int id) throws BusinessException {
		boolean b = false;
		adminDAO.getCustomerById(id);
		if (id > 1000 && id < 100000){
			b = true;
      }
		return b;
	}
	
	@Override
	public boolean getAccountsByStatus(String status) throws BusinessException {
		boolean b = false;
		adminDAO.getAccountsByStatus(status);
		if (status.equals("active"))  {
			b = true;
		} else if (status.equals("pending")){
			b = true;
			} else { 
				b = false;
			}
		return b;
	
	}
	
	@Override
	public boolean updateAccountStatus(String status, long account_number) throws BusinessException {
		boolean b = false;
		adminDAO.updateAccountStatus(status, account_number);
		if(status.equals("active") && account_number>1000000000L && account_number<9999999999L) {
			b = true;
		}else if(status.equals("pending") && account_number>1000000000L && account_number<9999999999L) {
			b = true;
		}else {
			 b = false;
		}
		return b;
	}
	
//----------------------------------Customer---------------------------------------------------

	@Override
	public boolean getAccountsById(int customer_id) throws BusinessException {
		custDAO.getAccountsById(customer_id);
		boolean b = false;
		if (customer_id > 1000 && customer_id < 100000) {
			b = true; 
		}
		return b; 
	}
	
	@Override
	public boolean getTransactionById(long transaction_id) throws BusinessException{
		custDAO.getTransactionById(transaction_id);
		boolean b = false;
		if(transaction_id>1000000L) {
			b = true; 
		}
		return b; 
	}
	
	@Override
	public boolean getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException {
		custDAO.getTtransfersByAccNumber(receiver_acc_num);
		boolean b = false;
		if(receiver_acc_num>1000000000L &&receiver_acc_num<9999999999L) {
			b = true; 
		}
		return b; 
	}
//----------------------------------Login---------------------------------------------------
	
	@Override
	public boolean employeeLogin(String employee_id, String password) throws BusinessException {
		boolean b = false;
		logDAO.employeeLogin(employee_id, password);
		if(employee_id.matches("[a-zA-Z]{2}[0-9]{4}") && password.matches("[a-zA-Z0-9]{4,12}")) {
			b = true;
		}
		return b;
	}
	
	@Override
	public boolean letCustomerLogin(String login, String password) throws BusinessException {
		boolean b = false;
		logDAO.letCustomerLogin(login, password);
		if(login.matches("[a-zA-Z0-9]{4,12}")&& password.matches("[a-zA-Z0-9]{4,12}")) {
			b = true;
		}
		return b;
	}
	
//----------------------------------Transaction---------------------------------------------------	
	
	@Override
	public boolean processTransfer(double newBalance, long receiver_acc_num, long transaction_id, String type) throws BusinessException {
		transDAO.processTransfer(newBalance, receiver_acc_num, transaction_id, type);
		return true;
	}
	

//	public boolean createLogin(CustomerLogin customer_login) throws BusinessException{
//	custDAO.createLogin(customer_login);
//	boolean b = false;
//	if(customer_login.getLogin().matches("[a-zA-Z0-9]{4,12})") && customer_login.getPassword().matches("[a-zA-Z0-9]{4,12})")) {
//		b = true; 
//	}
//	return b; 
//}	
}
