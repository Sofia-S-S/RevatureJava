package com.sverbank.model;

import java.util.Date;

public class Customer {
	
	private int id;
	private String first_name;
	private String last_name;
	private String gender;
	private Date dob;
	private long ssn;
	private long contact;
	private String address;
	
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}


	public Customer(int id, String first_name, String last_name, String gender, Date dob, long ssn, long contact,
			String address) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.dob = dob;
		this.ssn = ssn;
		this.contact = contact;
		this.address = address;
	}
	
	// New Customer Constructor

	public Customer( String first_name, String last_name, String gender, Date dob, long ssn, long contact,
			String address) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.dob = dob;
		this.ssn = ssn;
		this.contact = contact;
		this.address = address;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDob() {
		return dob;
	}

	public long getSsn() {
		return ssn;
	}


	public void setSsn(long ssn) {
		this.ssn = ssn;
	}


	public long getContact() {
		return contact;
	}


	public void setContact(long contact) {
		this.contact = contact;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		String i = String.format( "%-6s", id); 
		String fn = String.format( "%-10s", first_name); 
		String ln = String.format( "%-10s", last_name); 
		return " Customer id : " + i + " | First name : " + fn + " | Last name : " + ln + " | Gender : " + gender
				+ " | Dob : " + dob + " | SSN : " + ssn + " | Phone number : +1 " + contact + " | Address=" + address;
	}

}
