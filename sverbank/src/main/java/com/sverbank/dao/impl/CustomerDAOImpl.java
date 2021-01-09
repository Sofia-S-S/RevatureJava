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



	@Override
	public Customer getCustomerById(int id) throws BusinessException {
		Customer customer = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
			String sql = "select first_name,last_name,gender,dob,ssn,address,contact from sverbank.customer where id=?";
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
				customer.setContact(resultSet.getLong("contact"));
				
			}else {
				throw new BusinessException("No customer found with id "+id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		} 
		return customer; //always start your code with fixing auto generated return
	}
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
			
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return c;
	}
	
//-------------------------------get id after customer logged in---------------------------

	@Override
	public CustomerLogin letCustomerLogin(String login, String password) throws BusinessException {
		CustomerLogin customer_login = null;
		
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select customer_id from sverbank.customer_login where login=? AND password=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query Executed");
			if (resultSet.next()) {
				System.out.println("If in DAO");
				customer_login = new CustomerLogin();
				customer_login.setLogin(login);
				customer_login.setPassword(password);
				customer_login.setCustomer_id(resultSet.getInt("customer_id"));

			} else {
				System.out.println("else in dao");
				throw new BusinessException("Wrong login or password");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("exception in DAO");
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		
		return customer_login;
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
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return accountsList;
	}
// ------------------------update balance ( add/ withdraw)------------------------------------------

	@Override
	public Account updateAccountBalance(long account_number, double newBalance) throws BusinessException {
		Account account=null;
		try (Connection connection=PostresqlConnection.getConnection()){	
		String sql="update sverbank.account set balance=? where account_number=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);

		preparedStatement.setDouble(1, newBalance);
		preparedStatement.setLong(2, account_number);
		preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return account;
	}

	// ------------------------get one account by account number------------------------------------------
	
	@Override
	public Account getAccountByNumber(long account_number, String status) throws BusinessException {
		Account account=null;
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select balance from sverbank.account where account_number=? AND status=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account_number);
			preparedStatement.setString(2, status);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account = new Account();
				account.setAccount_number(account_number);
				account.setStatus(status);
				account.setBalance(resultSet.getDouble("balance"));

			} else {
				System.out.println("getAccountByNumber DAO fail");
				throw new BusinessException("Wrong account number");

			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return account;
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
				throw new BusinessException("Wrong status");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("getAccountsByStatus DAO fail");
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return accountsList;
	}

	@Override
	public Account updateAccountStatus(String status,long account_number) throws BusinessException {
		Account account=null;
		try (Connection connection=PostresqlConnection.getConnection()){	
		String sql="update sverbank.account set status=? where account_number=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, status);
		preparedStatement.setLong(2, account_number);
		preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return account;
	}



}
