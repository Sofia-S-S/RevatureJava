package com.sverbank.service;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Transaction;

public interface TransactionService {

	public int cashOperation(Transaction transaction, long account_number, double newBalance) throws BusinessException;
	public void createTransactionTransfer (Transaction transfer,long account_number, double newBalance ) throws BusinessException ;
	public int processTransfer(double newBalance,long account_number, long transaction_id, String type) throws BusinessException;
}
