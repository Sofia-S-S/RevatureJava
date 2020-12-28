package com.app.model;

import java.util.Date;

//1

public class Player {
	
	private int id;
	private String name;
	private int age;
	private long contact;
	private String gender;
	private String team_name;
	private Date dob;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(int id, String name, int age, long contact, String gender, String team_name, Date dob) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.contact = contact;
		this.gender = gender;
		this.team_name = team_name;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", age=" + age + ", contact=" + contact + ", gender=" + gender
				+ ", team_name=" + team_name + ", dob=" + dob + "]";
	}
	
	
	

}
