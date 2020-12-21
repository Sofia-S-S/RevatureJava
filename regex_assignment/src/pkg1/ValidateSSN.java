package pkg1;

//task1 - write a program to validate an SSN number using regex

public class ValidateSSN {
	
	public static void main(String[] args) {
		
		String x="333-22-4444";
		if(x.matches("[0-9]{3}-[0-9]{2}-[0-9]{4}")) {
			System.out.println("Valid SSN");
		}else {
			System.out.println("Invalid SSN");
		}
		
	}

}
