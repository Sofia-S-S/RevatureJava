package com.sverbank.model;

public class Account {
	
	private int customer_id;
	private long account_number;
	private double balance;
	private String status;
	
	public Account() {

	}

	public Account(int customer_id, long account_number, double balance, String status) {
		super();
		this.customer_id = customer_id;
		this.account_number = account_number;
		this.balance = balance;
		this.status = status;
	}
	
	// Constructor for New Account
	public Account(int customer_id, double balance, String status) {
		super();
		this.customer_id = customer_id;
		this.balance = balance;
		this.status = status;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public long getAccount_number() {
		return account_number;
	}

	public void setAccount_number(long account_number) {
		this.account_number = account_number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [customer_id=" + customer_id + ", account_number=" + account_number + ", balance=" + balance
				+ ", status=" + status + "]";
	}
	

}
