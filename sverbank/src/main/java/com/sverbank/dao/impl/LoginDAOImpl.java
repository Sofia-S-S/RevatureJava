package com.sverbank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sverbank.dao.LoginDAO;
import com.sverbank.dao.dbutil.PostresqlConnection;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;

public class LoginDAOImpl implements LoginDAO {
	
//------------------------------------EMPLOYEE LOG IN---------------------------------------------	

	@Override
	public Employee employeeLogin(String employee_id, String password) throws BusinessException {
		Employee employee = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select name from sverbank.employee where employee_id=? and password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, employee_id);
			preparedStatement.setString(2, password);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				//if we found a player -> instantiate player object and set values to it
				employee = new Employee();
				employee.setEmployee_id(employee_id);
				employee.setPassword(password);
				employee.setName(resultSet.getString("name"));
			
			}else {
				throw new BusinessException("No employee found with id and password ");
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		} 
		return employee;
	}

//-----------------CUSTOMER LOG IN -------------get id after customer logged in---------------------------

		@Override
		public CustomerLogin letCustomerLogin(String login, String password) throws BusinessException {
			CustomerLogin customer_login = null;
			
			try (Connection connection = PostresqlConnection.getConnection()) {
				String sql = "select customer_id from sverbank.customer_login where login=? AND password=?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, password);
				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {

					customer_login = new CustomerLogin();
					customer_login.setLogin(login);
					customer_login.setPassword(password);
					customer_login.setCustomer_id(resultSet.getInt("customer_id"));

				} else {

					throw new BusinessException("No customer found with such login or password");
				}
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("exception in DAO");

				throw new BusinessException("Internal error occured contact admin ");
			}
			
			return customer_login;
		}

}
