package eg1;

public class Palindrome {

	public static void main(String[] args) {
		
		String s="madam";
		StringBuffer sb=new StringBuffer(s);
		sb.reverse();
		String s1=sb.toString();
		if(s.equals(s1)) {
			System.out.println("Yes it is Palindrome");
		}else {
			System.out.println("No it is not palindrome");
		}
		
		
		int num=101;
		s=num+"";
		System.out.println("s = "+s);
		if(new StringBuffer(s).reverse().toString().equals(s)) {
			System.out.println("Yep it is Palindrome");
		}else {
			System.out.println("Nope it is not palindrome");
		}
		
	

		
//Task  - Print all palindromes between 1000 and 9999 with the above style	
		
		System.out.println("\nAll palindromes between 1000 and 9999 :");

		for (int x = 1000; x < 9999; x++) {
			String y=x+"";

		if(new StringBuffer(y).reverse().toString().equals(y)) {
			System.out.print(y+" ");
		}
		}
	}

}


