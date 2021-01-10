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
import com.sverbank.model.Transfer;


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
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return up;
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
	@Override
	public int createTransfer(Transfer transfer) throws BusinessException {
		int t = 0;
		try (Connection connection=PostresqlConnection.getConnection()){
			
			String sql="insert into sverbank.transfer (sender_acc_num, receiver_acc_num, amount, date) values(?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, transfer.getSender_acc_num());
			preparedStatement.setLong(2, transfer.getReceiver_acc_num());
			preparedStatement.setDouble(3, transfer.getAmount());
			preparedStatement.setDate(4, new java.sql.Date(transfer.getDate().getTime()));
			
			t = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return t;
	}

	@Override
	public void sendMoney(Transfer transfer, long account_number, double newBalance) throws BusinessException {

		try (Connection connection=PostresqlConnection.getConnection()){
			
			String updateBalance ="update sverbank.account set balance=? where account_number=?";
			String createTransfer ="insert into sverbank.transfer (sender_acc_num, receiver_acc_num, amount, date) values(?,?,?,?)";
			
			PreparedStatement preparedStatementBalance=connection.prepareStatement(updateBalance);
			PreparedStatement preparedStatementTransfer=connection.prepareStatement(createTransfer);
			
			connection.setAutoCommit(false); // !!!
			
			preparedStatementBalance.setDouble(1, newBalance);
			preparedStatementBalance.setLong(2, account_number);
			preparedStatementBalance.executeUpdate();
			
			preparedStatementTransfer.setLong(1, transfer.getSender_acc_num());
			preparedStatementTransfer.setLong(2, transfer.getReceiver_acc_num());
			preparedStatementTransfer.setDouble(3, transfer.getAmount());
			preparedStatementTransfer.setDate(4, new java.sql.Date(transfer.getDate().getTime()));
			preparedStatementTransfer.executeUpdate();
			
			connection.commit(); // !!!
			
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
		}

	}

	@Override
	public void approveTransfer(double newBalance, long receiver_acc_num, int transfer_id) throws BusinessException {

		try (Connection connection=PostresqlConnection.getConnection()){
			
			String updateBalance ="update sverbank.account set balance=? where account_number=?";
			String deleteTransfer ="delete from sverbank.transfer where transfer_id=? and receiver_acc_num=?";
			
			PreparedStatement preparedStatementBalance=connection.prepareStatement(updateBalance);
			PreparedStatement preparedStatementTransfer=connection.prepareStatement(deleteTransfer);
			
			connection.setAutoCommit(false); // !!!
			
			preparedStatementBalance.setDouble(1, newBalance);
			preparedStatementBalance.setLong(2, receiver_acc_num);
			preparedStatementBalance.executeUpdate();
			
			preparedStatementTransfer.setInt(1, transfer_id);
			preparedStatementTransfer.setLong(2, receiver_acc_num);
			preparedStatementTransfer.executeUpdate();
			
			connection.commit(); // !!!
			
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);
			
			throw new BusinessException("Some internal error occured. Please contact admin");
			}
		
	}

	@Override
	public List<Transfer> getTtransfersByAccNumber(long receiver_acc_num) throws BusinessException {
		List<Transfer> transfersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select transfer_id, sender_acc_num, amount, date from sverbank.transfer where receiver_acc_num = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, receiver_acc_num);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transfer transfer =new Transfer();
				transfer.setReceiver_acc_num(receiver_acc_num);
				transfer.seTtransfer_id(resultSet.getInt("transfer_id"));
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
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return transfersList;
	}

	@Override
	public Transfer getTransferById(int transfer_id) throws BusinessException {
		Transfer transfer=null;
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select receiver_acc_num, sender_acc_num, amount, date from sverbank.transfer where transfer_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, transfer_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				transfer = new Transfer();
				transfer.setSender_acc_num(resultSet.getLong("receiver_acc_num"));
				transfer.setReceiver_acc_num(resultSet.getLong("receiver_acc_num"));
				transfer.setAmount(resultSet.getDouble("amount"));
				transfer.setDate(resultSet.getDate("date"));

			} else {
				System.out.println("getTransferById DAO fail");
				throw new BusinessException("No trunsfer found with id "+transfer_id);

			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return transfer;
	}



}
	

