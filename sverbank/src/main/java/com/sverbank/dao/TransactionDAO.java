package com.sverbank.dao;



import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Transaction;

public interface TransactionDAO {

	public int cashOperation(Transaction transaction, long account_number, double newBalance) throws BusinessException;
	public void createTransactionTransfer (Transaction transfer,long account_number, double newBalance ) throws BusinessException ;
	public void processTransfer(double newBalance,long account_number, long transaction_id, String type) throws BusinessException;
	
}
