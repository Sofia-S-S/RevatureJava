package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.dao.CustomerDAO;
import com.sverbank.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public int createCustomer(Customer customer) throws BusinessException {
		int c = 0;
		try (Connection connection=PostresqlConnection.getConnection()){
			
			String sql="insert into sverbank.customers(id,first_name,last_name,gender,dob,ssn,address,phone_number) values(?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer.getId());
			preparedStatement.setString(2, customer.getFirst_name());
			preparedStatement.setString(3, customer.getLast_name());
			preparedStatement.setString(4, customer.getGender());
			preparedStatement.setDate(5, new java.sql.Date(customer.getDob().getTime()));
			preparedStatement.setLong(6, customer.getSsn());
			preparedStatement.setString(7, customer.getAddress());
			preparedStatement.setLong(8, customer.getContact());

			
			c = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}

	@Override
	public int approveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getCustomerById(int id) throws BusinessException {
		Customer customer = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select first_name,last_name,gender,dob,ssn,address,phone_number from sverbank.customers where id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				//if we found a player -> instantiate player object and set values to it
				customer = new Customer();
				customer.setId(id);
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setGender(resultSet.getString("gender"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setSsn(resultSet.getLong("ssn"));
				customer.setAddress(resultSet.getString("address"));
				customer.setContact(resultSet.getLong("phone_number"));
				
			}else {
				throw new BusinessException("No customer found with id "+id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		} 
		return customer; //always start your code with fixing auto generated return
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

}
