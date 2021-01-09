package com.sverbank.model;

public class CustomerLogin {
	
	private int customer_id;
	private String login;
	private String password;
	
	public CustomerLogin() {}

	public CustomerLogin(int customer_id, String login, String password) {
		super();
		this.customer_id = customer_id;
		this.login = login;
		this.password = password;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CustomerLogin   [ customer_id = " + customer_id + " |  login = " + login + " | password=" + password + " ]";
	}
	 
	 


}
