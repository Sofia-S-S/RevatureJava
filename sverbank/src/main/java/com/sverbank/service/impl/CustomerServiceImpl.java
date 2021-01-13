package com.sverbank.service.impl;

import java.util.List;
import java.util.Scanner;

import com.sverbank.dao.CustomerDAO;
import com.sverbank.dao.impl.CustomerDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Transaction;
import com.sverbank.service.CustomerService;


public class CustomerServiceImpl implements CustomerService{
	
	Scanner sc = new Scanner(System.in);
	
	
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	
//===================================== CREATE ======================================================

	@Override
	public int createCustomer(Customer customer) throws BusinessException {
		int x = 0;
		if(customer.getFirst_name().matches("[a-zA-Z]{2,30}")) {
			if(customer.getLast_name().matches("[a-zA-Z]{2,30}")) {
				if(customer.getGender().matches("[MF]{1}")) {
					if(customer.getSsn()>100000000L && customer.getSsn()<999999999L) {
						if(customer.getAddress()!=null) {
							if(customer.getContact()>1000000000L && customer.getContact()<9999999999L) {
							x=customerDAO.createCustomer(customer);
							} else {
								throw new BusinessException("Phone number "+customer.getContact()+" is INVALID");
							}
						} else {
							throw new BusinessException("Address "+customer.getAddress()+" is INVALID");
						}
					} else {
						throw new BusinessException("Social Security Number "+customer.getSsn()+" is INVALID");
					}
				} else {
					throw new BusinessException("Entered Gender "+customer.getGender()+" is INVALID");

				}
			} else {
				throw new BusinessException("Entered Last Name "+customer.getLast_name()+" is INVALID");
			}
		} else {
			throw new BusinessException("Entered First Name "+customer.getFirst_name()+" is INVALID");
		}
		return x;
	}


	@Override
	public int createLogin(CustomerLogin customer_login) throws BusinessException {
		int x = 0;
		if(customer_login.getLogin().matches("[a-zA-Z0-9]{4,12}") && customer_login.getPassword().matches("[a-zA-Z0-9]{4,12}")) {
			x = customerDAO.createLogin(customer_login);
		} else {
			throw new BusinessException("Login or password is INVALID. Please call bank to complite registration");
		}
		return x;
	}

	@Override
	public int createAccount(Account account) throws BusinessException {
		int acc = customerDAO.createAccount(account);
		return acc;
	}
	
//================================= GET Accounts ========================================================

	//---------------------get all accounts of one Customer by id-----------------------------------		
	@Override
	public List<Account> getAccountsById(int customer_id) throws BusinessException {
		List<Account> accountsList = null;
		if (customer_id > 1000 && customer_id < 100000) {

			accountsList = customerDAO.getAccountsById(customer_id);
		} else {
			throw new BusinessException("Customer Id " + customer_id + " is INVALID......");
		}
		return accountsList;
	}

	// ------------------------get one account by account number------------------------------------------
		@Override
	public Account getAccountByNumber(long account_number) throws BusinessException {
		Account account = null;
		if(account_number>1000000000L && account_number<9999999999L) {

			account = customerDAO.getAccountByNumber(account_number);
		}else {
			throw new BusinessException("Account Number " + account_number + " is INVALID");
		}
		return account;
	}


//==================================== GET Transactions =============================================

	// ------------------------get one transaction by id-----------------------------------------
	@Override
	public Transaction getTransactionById(long transaction_id) throws BusinessException {
		Transaction transaction = null;
		if(transaction_id>1000000L) {
		transaction = customerDAO.getTransactionById(transaction_id);
		}else {
			throw new BusinessException("Transaction id " + transaction_id + " is INVALID");
		}
		return transaction;
	}

	// ------------------------get all pending transfers for an one account -----------------------------------
	@Override
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException {
		List<Transaction> transactionsList = null;
		if(receiver_acc_num>1000000000L &&receiver_acc_num<9999999999L) {
		transactionsList = customerDAO.getTtransfersByAccNumber(receiver_acc_num);
		}else {
			throw new BusinessException("Account Number " + receiver_acc_num + " is INVALID");
		}
		return transactionsList;
	}
	









}
