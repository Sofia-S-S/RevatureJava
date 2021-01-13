package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sverbank.dao.TransactionDAO;
import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {

	// ------------------------update balance and create transaction ( add/ withdraw) ------------------------------------------

	@Override
	public int cashOperation(Transaction transaction, long account_number, double newBalance) throws BusinessException {
	int xy = 0;
		try (Connection connection=PostresqlConnection.getConnection()){	
			
		String sqlAccount="update sverbank.account set balance=? where account_number=?";
		String sqlTransaction="insert into sverbank.transaction (type, sender_acc_num, receiver_acc_num, amount, date) values(?,?,?,?,?)";
		
		PreparedStatement preparedStatementAccount=connection.prepareStatement(sqlAccount);
		PreparedStatement preparedStatementTransaction=connection.prepareStatement(sqlTransaction);
		
		connection.setAutoCommit(false); // !!!

		preparedStatementAccount.setDouble(1, newBalance);
		preparedStatementAccount.setLong(2, account_number);
		int x = preparedStatementAccount.executeUpdate();
		
		preparedStatementTransaction.setString(1, transaction.getType());
		preparedStatementTransaction.setLong(2, transaction.getSender_acc_num());
		preparedStatementTransaction.setLong(3, transaction.getReceiver_acc_num());
		preparedStatementTransaction.setDouble(4, transaction.getAmount());
		preparedStatementTransaction.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
		int y = preparedStatementTransaction.executeUpdate();
		
		connection.commit(); // !!!
		xy = x+y;
		
		} catch (ClassNotFoundException | SQLException e) {
	
		
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return xy;
		
	}

	// ------------------------update balance and create transaction ( transfer ) ------------------------------------------
	@Override
	public void createTransactionTransfer(Transaction transaction, long account_number, double newBalance) throws BusinessException {

		try (Connection connection=PostresqlConnection.getConnection()){
			
			String updateBalance ="update sverbank.account set balance=? where account_number=?";
			String createTransfer ="insert into sverbank.transaction (type, sender_acc_num, receiver_acc_num, amount, date) values(?,?,?,?,?)";
			
			PreparedStatement preparedStatementBalance=connection.prepareStatement(updateBalance);
			PreparedStatement preparedStatementTransfer=connection.prepareStatement(createTransfer);
			
			connection.setAutoCommit(false); // !!!
			
			preparedStatementBalance.setDouble(1, newBalance);
			preparedStatementBalance.setLong(2, account_number);
			preparedStatementBalance.executeUpdate();
			
			preparedStatementTransfer.setString(1, transaction.getType());
			preparedStatementTransfer.setLong(2, transaction.getSender_acc_num());
			preparedStatementTransfer.setLong(3, transaction.getReceiver_acc_num());
			preparedStatementTransfer.setDouble(4, transaction.getAmount());
			preparedStatementTransfer.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
			preparedStatementTransfer.executeUpdate();
			
			connection.commit(); // !!!
			
		} catch (ClassNotFoundException | SQLException e) {
			
				
			throw new BusinessException("Some internal error occured. Please contact admin");
		}

	}

	// ------------------------update balance and update type of transaction ( accepted / declined ) ------------------------------------------
	@Override
	public void processTransfer(double newBalance, long account_number, long transaction_id, String type) throws BusinessException {

		try (Connection connection=PostresqlConnection.getConnection()){
			
			String updateBalance ="update sverbank.account set balance=? where account_number=?";
			String deleteTransfer ="update sverbank.transaction set type=? where transaction_id=? ";
			
			PreparedStatement preparedStatementBalance=connection.prepareStatement(updateBalance);
			PreparedStatement preparedStatementTransfer=connection.prepareStatement(deleteTransfer);
			
			connection.setAutoCommit(false); // !!!

			preparedStatementBalance.setDouble(1, newBalance);
			preparedStatementBalance.setLong(2, account_number);
			preparedStatementBalance.executeUpdate();
			
			preparedStatementTransfer.setString(1, type);
			preparedStatementTransfer.setLong(2, transaction_id);
			preparedStatementTransfer.executeUpdate();
			
			connection.commit(); // !!!
			
		} catch (ClassNotFoundException | SQLException e) {
			
		
			throw new BusinessException("Some internal error occured. Please contact admin");
			}
		
	}

}
