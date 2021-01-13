package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.sverbank.dao.AdministrationDAO;
import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.Transaction;

public class AdministrationDAOImpl implements AdministrationDAO {
	
	
// =====================================GET CUSTOMER===========================================
	
	//---------------------------------get customer by id---------------------------------
	@Override
	public Customer getCustomerById(int id) throws BusinessException {
		Customer customer = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select first_name,last_name,gender,dob,ssn,address,contact from sverbank.customer where id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){

				customer = new Customer();
				customer.setId(id);
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setGender(resultSet.getString("gender"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setSsn(resultSet.getLong("ssn"));
				customer.setAddress(resultSet.getString("address"));
				customer.setContact(resultSet.getLong("contact"));
				
			}else {
				throw new BusinessException("No customer found with id "+id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		} 
		return customer;
	}
	
	//---------------------------------get customer by SSN---------------------------------
	@Override
	public Customer getCustomerBySSN(long ssn) throws BusinessException {
		Customer customer = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select id, first_name,last_name,gender,dob,address,contact from sverbank.customer where ssn=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, ssn);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){

				customer = new Customer();
				customer.setSsn(ssn);
				customer.setId(resultSet.getInt("id"));
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setGender(resultSet.getString("gender"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setAddress(resultSet.getString("address"));
				customer.setContact(resultSet.getLong("contact"));

				
			}else {
				throw new BusinessException("No customer not found with ssn "+ssn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		} 
		return customer;
	}
	
//======================================LISTS===================================================
	
	//------------------------------get all customers-----------------------------------------
	@Override
	public List<Customer> getAllCustomers() throws BusinessException {
		List<Customer> customersList = new ArrayList<>();
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select id, first_name,last_name,gender,dob,ssn,address,contact from sverbank.customer";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){

				Customer customer = new Customer();
				customer.setId(resultSet.getInt("id"));
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setGender(resultSet.getString("gender"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setSsn(resultSet.getLong("ssn"));
				customer.setAddress(resultSet.getString("address"));
				customer.setContact(resultSet.getLong("contact"));
				customersList.add(customer);
				
			} if (customersList.size()==0)	
			{
				throw new BusinessException("No customes are found in the system");
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		
		} 
		return customersList;
	}

	// ------------------------get all accounts by status ------------------------------------------
	@Override
	public List<Account> getAccountsByStatus(String status) throws BusinessException {
		List<Account> accountsList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select * from sverbank.account where status=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, status);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account account = new Account();
				account.setStatus(status);
				account.setCustomer_id(resultSet.getInt("customer_id"));
				account.setAccount_number(resultSet.getLong("account_number"));
				account.setBalance(resultSet.getDouble("balance"));
				accountsList.add(account);

			} if(accountsList.size()==0) {
				throw new BusinessException("No accounts fount in a system with status "+status);
			}
		} catch (ClassNotFoundException | SQLException e) {

			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return accountsList;
	}

	//------------------------------get all transactions -----------------------------------------
	@Override
	public List<Transaction> getAllTransactions() throws BusinessException {
		List<Transaction> transactionsList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select transaction_id, type, sender_acc_num, amount, receiver_acc_num, date from sverbank.transaction ";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction transaction =new Transaction();
				transaction.setTransaction_id(resultSet.getLong("transaction_id"));
				transaction.setType(resultSet.getString("type"));
				transaction.setReceiver_acc_num(resultSet.getLong("receiver_acc_num"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setSender_acc_num(resultSet.getLong("sender_acc_num"));
				transaction.setDate(resultSet.getDate("date"));
				transactionsList.add(transaction);
			}
			if(transactionsList.size()==0)
			{
				throw new BusinessException("There is no transaction in the system");
			}
		}catch (ClassNotFoundException | SQLException e) {

			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return transactionsList;
	}
	// ------------------------update account status ( active / pending)  ------------------------------------------
	@Override
	public int updateAccountStatus(String status,long account_number) throws BusinessException {
		int up=0;
		try (Connection connection=PostresqlConnection.getConnection()){	
		String sql="update sverbank.account set status=? where account_number=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, status);
		preparedStatement.setLong(2, account_number);
		preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {

			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return up;
	}

}
