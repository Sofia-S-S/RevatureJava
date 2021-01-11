package com.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightMain {
public static void main(String[] args) {


	List<Flight> flightList = new ArrayList<>();
	flightList.add(new Flight(5670, "Anerican Airlines",	250.60, 4.5f, LocalDate.parse("11.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), true ));
	flightList.add(new Flight(3046, "United",          		235.70, 4.3f, LocalDate.parse("10.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), false));
	flightList.add(new Flight(7315, "Spirit", 				130.90, 3.5f, LocalDate.parse("10.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), false ));
	flightList.add(new Flight(5680, "Anerican Airlines",	246.60, 4.6f, LocalDate.parse("10.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), false ));
	flightList.add(new Flight(1514, "Delta", 				170.40, 3.8f, LocalDate.parse("11.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), true ));
	flightList.add(new Flight(8809, "Frontier", 			130.90, 3.5f, LocalDate.parse("10.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), true ));
	flightList.add(new Flight(1515, "Delta", 				180.40, 3.8f, LocalDate.parse("11.01.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")), false ));
	
	
	//-----------------------sort by cost---------------------------
	System.out.println("\n\nList of all flights :");
	printFlights(flightList);

	Collections.sort(flightList, (Flight f1, Flight f2) -> {
		Double d1 = f1.getCost();
		Double d2 = f2.getCost();
		return d1.compareTo(d2); //from low to high
	});
	System.out.println("\n\nAll Flights from Low to High cost");
	printFlights(flightList);
	
	//-----------------------sort by airlines name---------------------------
	System.out.println("List of all flights by  airlines name:");
	printFlights(flightList);

	Collections.sort(flightList, (Flight f1, Flight f2) -> {

		return f1.getAirlines().compareTo(f2.getAirlines()); //in alphabetic order
	});
	System.out.println("\n\nAll Flights from Low to High cost");
	printFlights(flightList);
	}

	private static void printFlights(List<Flight> flightList) {
		for (Flight fl : flightList) {
			System.out.println(fl);
		}
	}
	
}

