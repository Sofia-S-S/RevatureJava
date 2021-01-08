package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.dao.CustomerDAO;
import com.sverbank.model.Account;
import com.sverbank.model.Customer;
import com.sverbank.model.NewCustomer;

public class CustomerDAOImpl implements CustomerDAO {

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
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}

	@Override
	public Customer getCustomerBySSN(long ssn) throws BusinessException {
		Customer customer = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select id from sverbank.customer where ssn=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, ssn);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				//if we found a player -> instantiate player object and set values to it
				customer = new Customer();
				customer.setSsn(ssn);
				customer.setId(resultSet.getInt("id"));

				
			}else {
				throw new BusinessException("No customer not found with ssn "+ssn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		} 
		return customer; //always start your code with fixing auto generated return
	}


//
//	@Override
//	public Customer getCustomerById(int id) throws BusinessException {
//		Customer customer = null;
//		try (Connection connection=PostresqlConnection.getConnection()){	
//			String sql = "select first_name,last_name,gender,dob,ssn,address,contact from sverbank.customer where id=?";
//			PreparedStatement preparedStatement=connection.prepareStatement(sql);
//			preparedStatement.setInt(1, id);
//			ResultSet resultSet=preparedStatement.executeQuery();
//			if(resultSet.next()){
//				//if we found a player -> instantiate player object and set values to it
//				customer = new Customer();
//				customer.setId(id);
//				customer.setFirst_name(resultSet.getString("first_name"));
//				customer.setLast_name(resultSet.getString("last_name"));
//				customer.setGender(resultSet.getString("gender"));
//				customer.setDob(resultSet.getDate("dob"));
//				customer.setSsn(resultSet.getLong("ssn"));
//				customer.setAddress(resultSet.getString("address"));
//				customer.setContact(resultSet.getLong("contact"));
//				
//			}else {
//				throw new BusinessException("No customer found with id "+id);
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			
//			System.out.println(e);//take off this lane when app is live
//			throw new BusinessException("Some internal error occured. Please contact admin");
//		
//			
//		} 
//		return customer; //always start your code with fixing auto generated return
//	}
//
//	@Override
//	public List<Customer> getAllCustomers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
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
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}



}
