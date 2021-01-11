package com.app;

import java.time.LocalDate;


public class Flight {
	
	private int number;
	private String airlines;
	private double cost;
	private float ratings;
	private LocalDate date;
	private boolean available;


	public Flight(int number, String airlines, double cost, float ratings, LocalDate date, boolean available) {
		super();
		this.number = number;
		this.airlines = airlines;
		this.cost = cost;
		this.ratings = ratings;
		this.date = date;
		this.available = available;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAirlines() {
		return airlines;
	}

	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public float getRatings() {
		return ratings;
	}

	public void setRatings(float ratings) {
		this.ratings = ratings;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isAvailable() { //For boolean we have "is" instead of "get"
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	



	@Override
	public String toString() {
		return "Flight   [ number = " + number + " | airlines = " + String.format("%-20s", airlines) + " | cost = " + cost + " | ratings = " + ratings
				+ " | date = " + date + " | available = " + String.format("%-5s",available )+ " ]";
	}
	
	

}
