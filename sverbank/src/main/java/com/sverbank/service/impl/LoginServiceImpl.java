package com.sverbank.service.impl;


import com.sverbank.dao.LoginDAO;
import com.sverbank.dao.impl.LoginDAOImpl;
import com.sverbank.exeption.BusinessException;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;
import com.sverbank.service.LoginService;

public class LoginServiceImpl implements LoginService{
	
	private LoginDAO loginDAO = new LoginDAOImpl();
	
	@Override
	public Employee employeeLogin(String employee_id, String password) throws BusinessException {
		Employee employee =null;
		if(employee_id.matches("[a-zA-Z]{2}[0-9]{4}")&& password.matches("[a-zA-Z0-9]{4,12}")) {
			System.out.println("employeeLogin service passed");
			employee =loginDAO.employeeLogin(employee_id, password);
		}else {
			throw new BusinessException("Entered loging or password is INVALID");
		}
		return employee;
	}
	
	@Override
	public CustomerLogin letCustomerLogin(String login, String password) throws BusinessException {
		CustomerLogin customer_login =null;
		if(login.matches("[a-zA-Z0-9]{4,12}")&& password.matches("[a-zA-Z0-9]{4,12}")) {
			System.out.println("letCustomerLogin service passed");
			customer_login =loginDAO.letCustomerLogin(login, password);
		}else {
			throw new BusinessException("Entered loging or password is INVALID");
		}
		return customer_login;
	}

}
