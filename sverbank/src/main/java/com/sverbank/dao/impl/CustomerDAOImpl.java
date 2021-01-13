package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.dao.CustomerDAO;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.CustomerLogin;

import com.sverbank.model.Transaction;

public class CustomerDAOImpl implements CustomerDAO {
	
	
//===================================== CREATE ======================================================

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
			
			
			throw new BusinessException("Some internal error occured. Please contact Sverbank");
		}
		return c;
	}
	
//================================= GET Accounts ========================================================
	
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

			throw new BusinessException("Internal error occured contact admin ");
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

				throw new BusinessException("Wrong account number");

			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured contact admin ");
		}
		return account;
	}



//==================================== GET Transactions =============================================

	// ------------------------get one transaction by id-----------------------------------------

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

				throw new BusinessException("No trunsfer found with id "+transaction_id);

			}
		} catch (ClassNotFoundException | SQLException e) {

			throw new BusinessException("Internal error occured contact SYSADMIN getTransactionById");
		}
		return transaction;
	}

	// ------------------------get all pending transfers for an one account -----------------------------------

	@Override
	public List<Transaction> getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException {
		List<Transaction> transfersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select transaction_id, sender_acc_num, amount, date from sverbank.transaction where type ='transfer' and receiver_acc_num = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, receiver_acc_num);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction transfer =new Transaction();
				transfer.setReceiver_acc_num(receiver_acc_num);
				transfer.setTransaction_id(resultSet.getLong("transaction_id"));
				transfer.setSender_acc_num(resultSet.getLong("sender_acc_num"));
				transfer.setAmount(resultSet.getDouble("amount"));
				transfer.setDate(resultSet.getDate("date"));
				transfersList.add(transfer);
			}
			if(transfersList.size()==0)
			{
				throw new BusinessException("No awaiting transfers found for account number "+receiver_acc_num);
			}
		}catch (ClassNotFoundException | SQLException e) {

			throw new BusinessException("Internal error occured contact admin ");
		}
		return transfersList;
	}




}
	

