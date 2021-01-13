package com.sverbank.service.impl;


import com.sverbank.dao.TransactionDAO;
import com.sverbank.dao.impl.TransactionDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Transaction;
import com.sverbank.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	
	TransactionDAO transDAO = new TransactionDAOImpl();

	@Override
	public int cashOperation(Transaction transaction, long account_number, double newBalance) throws BusinessException {
		int x = transDAO.cashOperation(transaction, account_number, newBalance);
		return x;
	}

	@Override
	public void createTransactionTransfer(Transaction transfer, long account_number, double newBalance)	throws BusinessException {
		transDAO.createTransactionTransfer(transfer, account_number, newBalance);
	}

	@Override
	public int processTransfer(double newBalance, long account_number, long transaction_id, String type) throws BusinessException {
		int x =transDAO.processTransfer(newBalance, account_number, transaction_id, type);
		return x;
	}

}
