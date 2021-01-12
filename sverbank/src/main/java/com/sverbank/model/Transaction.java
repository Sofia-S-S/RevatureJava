package com.sverbank.model;

import java.util.Date;

public class Transaction{
	
	private long transaction_id;
	private String type;
	private long sender_acc_num;
	private long receiver_acc_num;
	private double amount;
	private Date date;
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(long transaction_id, String type, long sender_acc_num, long receiver_acc_num, double amount,
			Date date) {
		super();
		this.transaction_id = transaction_id;
		this.type = type;
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}
	
	public Transaction(String type, long sender_acc_num, long receiver_acc_num, double amount,
			Date date) {
		super();

		this.type = type;
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}

	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSender_acc_num() {
		return sender_acc_num;
	}

	public void setSender_acc_num(long sender_acc_num) {
		this.sender_acc_num = sender_acc_num;
	}

	public long getReceiver_acc_num() {
		return receiver_acc_num;
	}

	public void setReceiver_acc_num(long receiver_acc_num) {
		this.receiver_acc_num = receiver_acc_num;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Transaction [transaction_id=" + transaction_id + ", type=" + type + ", sender_acc_num=" + sender_acc_num
				+ ", receiver_acc_num=" + receiver_acc_num + ", amount=" + amount + ", date=" + date + "]";
	}
	
}
