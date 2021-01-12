package com.sverbank.dao;

import com.sverbank.exeption.BusinessException;
import com.sverbank.model.CustomerLogin;
import com.sverbank.model.Employee;

public interface LoginDAO {
	
	public Employee employeeLogin(String employee_id,String password) throws BusinessException;
	public CustomerLogin letCustomerLogin(String login, String password)throws BusinessException;

}
