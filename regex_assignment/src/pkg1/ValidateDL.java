package pkg1;

//task2 - write a program to validate DL 

public class ValidateDL {
	
	public static void main(String[] args) {
		
		String s="B666-8787-9005";
		if(s.matches("[A-Z]{1}[0-9]{3}-[0-9]{4}-[0-9]{4}")) {
			System.out.println("Valid DL");
		}else {
			System.out.println("Invalid DL");
		}

	}

}
