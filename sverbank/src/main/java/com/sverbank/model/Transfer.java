package com.sverbank.model;

import java.util.Date;

public class Transfer {
	
	private int transfer_id;
	private long sender_acc_num;
	private long receiver_acc_num;
	private double amount;
	private Date date;
	
	public Transfer() {
		// TODO Auto-generated constructor stub
	}

	public Transfer(int transfer_id, long sender_acc_num, long receiver_acc_num, double amount, Date date) {
		super();
		this.transfer_id = transfer_id;
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}
	
	// Constructor to create new Transfer without assigning id
	public Transfer(long sender_acc_num, long receiver_acc_num, double amount, Date date) {
		super();
		this.sender_acc_num = sender_acc_num;
		this.receiver_acc_num = receiver_acc_num;
		this.amount = amount;
		this.date = date;
	}

	public int geTtransfer_id() {
		return transfer_id;
	}

	public void seTtransfer_id(int transfer_id) {
		this.transfer_id = transfer_id;
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
		return "Transfer [transfer_id=" + transfer_id + ", sender_acc_num=" + sender_acc_num
				+ ", receiver_acc_num=" + receiver_acc_num + ", amount=" + amount + ", date=" + date + "]";
	}
	
}
