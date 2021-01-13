package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.main.BankMain;
import com.sverbank.dao.CustomerDAO;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

import com.sverbank.model.Transaction;

public class CustomerDAOImpl implements CustomerDAO {
	
	private static Logger log=Logger.getLogger(BankMain.class);  // Set up log

	@Override
	public int createCustomer(Customer customer) throws BusinessException {
		int c = 0;
		try (Connection connection=PostresqlConnection.getConnection()){
			
			String sql="insert into sverbank.customer(first_name,last_name,gender,dob,ssn,address,contact) values(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getFirst_name());
			preparedStatement.setString(2, customer.getLast_name());
			preparedStatement.setString(3, customer.getGender());
			preparedStatement.setDate(4, new java.sql.Date(customer.getDob().getTime()));
			preparedStatement.setLong(5, customer.getSsn());
			preparedStatement.setString(6, customer.getAddress());
			preparedStatement.setLong(7, customer.getContact());

			
			c = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			
			log.info(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}


	@Override
	public int createAccount(Account account) throws BusinessException {
		int c = 0;
		try (Connection connection=PostresqlConnection.getConnection()){
			
			String sql="insert into sverbank.account(customer_id,account_number,balance, status) values(?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getCustomer_id());
			preparedStatement.setLong(2, account.getAccount_number());
			preparedStatement.setDouble(3, account.getBalance());
			preparedStatement.setString(4, account.getStatus());
			
			c = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			
			log.info(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}

	@Override
	public int createLogin(CustomerLogin customer_login) throws BusinessException {
		int c = 0;
		try (Connection connection=PostresqlConnection.getConnection()){
			
			String sql="insert into sverbank.customer_login(customer_id,login, password) values(?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer_login.getCustomer_id());
			preparedStatement.setString(2, customer_login.getLogin());
			preparedStatement.setString(3, customer_login.getPassword());
			
			c = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			
			log.info(e);
			
			throw new BusinessException("Some internal error occured. Please contact Sverbank");
		}
		return c;
	}
	

	
//---------------------get all accounts of one Customer by id-----------------------------------	

	@Override
	public List<Account> getAccountsById(int customer_id) throws BusinessException {
		List<Account> accountsList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select account_number,balance,status from sverbank.account where customer_id = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer_id);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account =new Account();
				account.setCustomer_id(customer_id);
				account.setAccount_number(resultSet.getLong("account_number"));
				account.setBalance(resultSet.getDouble("balance"));
				account.setStatus(resultSet.getString("status"));
				accountsList.add(account);
			}
			if(accountsList.size()==0)
			{
				throw new BusinessException("No Accounts found for customer with id "+customer_id);
			}
		}catch (ClassNotFoundException | SQLException e) {
			log.info(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return accountsList;
	}



	// ------------------------get one account by account number------------------------------------------
	
	@Override
	public Account getAccountByNumber(long account_number) throws BusinessException {
		Account account=null;
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select customer_id, balance, status from sverbank.account where account_number=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account_number);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account = new Account();
				account.setAccount_number(account_number);
				account.setCustomer_id(resultSet.getInt("customer_id"));
				account.setBalance(resultSet.getDouble("balance"));
				account.setStatus(resultSet.getString("status"));
			} else {
				log.info("getAccountByNumber DAO fail");
				throw new BusinessException("Wrong account number");

			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return account;
	}


	// ------------------------update balance ( add/ withdraw)------------------------------------------

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
				log.info(e);//take off this lane when app is live
			
				throw new BusinessException("Some internal error occured. Please contact admin");
				
			}
			return xy;
			
		}


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
			
			log.info(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}

	}

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
			
			log.info(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
			}
		
	}



	@Override
	public Transaction getTransactionById(long transaction_id) throws BusinessException {
		Transaction transaction=null;
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select type, receiver_acc_num, sender_acc_num, amount, date from sverbank.transaction where transaction_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, transaction_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				transaction = new Transaction();
				transaction.setType(resultSet.getString("type"));
				transaction.setTransaction_id(transaction_id);
				transaction.setSender_acc_num(resultSet.getLong("receiver_acc_num"));
				transaction.setReceiver_acc_num(resultSet.getLong("receiver_acc_num"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setDate(resultSet.getDate("date"));

			} else {
				log.info("No trunsfer found with id "+transaction_id);
				throw new BusinessException("No trunsfer found with id "+transaction_id);

			}
		} catch (ClassNotFoundException | SQLException e) {
			log.info(e);
			throw new BusinessException("Internal error occured contact SYSADMIN getTransactionById");
		}
		return transaction;
	}


	@Override
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}










}
	

