package com.sverbank.model;

public class Employee {
	
	private String name;
	private String employee_id;
	private String password;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(String name, String employee_id, String password) {
		super();
		this.name = name;
		this.employee_id = employee_id;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", employee_id=" + employee_id + ", password=" + password + "]";
	}
	
	

}
