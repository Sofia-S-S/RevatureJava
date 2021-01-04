package com.app.model;

import java.util.Date;

public class Player {

	private int id;
	private String name;
	private int age;
	private long contact;
	private String teamname;
	private String gender;
	private Date dob;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(int id, String name, int age, long contact, String teamname, String gender, Date dob) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.contact = contact;
		this.teamname = teamname;
		this.gender = gender;
		this.dob = dob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", age=" + age + ", contact=" + contact + ", teamname="
				+ teamname + ", gender=" + gender + ", dob=" + dob + "]";
	}
	
}
