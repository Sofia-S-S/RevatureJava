package com.sverbank.model;

import java.util.Date;

public class Transaction {
	
	private int trunsaction_id;
	private long sender_acc_num;
	private long receiver_acc_num;
	private double amount;
	private Date date;
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(int trunsaction_id, long sender_acc_num, long receiver_acc_num, double amount, Date date) {
		super();
		this.trunsaction_id = trunsaction_id;
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}
	
	// Constructor to create new transaction without assigning id
	public Transaction(long sender_acc_num, long receiver_acc_num, double amount, Date date) {
		super();
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}

	public int getTrunsaction_id() {
		return trunsaction_id;
	}

	public void setTrunsaction_id(int trunsaction_id) {
		this.trunsaction_id = trunsaction_id;
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
		return "Transaction [trunsaction_id=" + trunsaction_id + ", sender_acc_num=" + sender_acc_num
				+ ", receiver_acc_num=" + receiver_acc_num + ", amount=" + amount + ", date=" + date + "]";
	}
	
}
