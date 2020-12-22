package pkg1;
//Create class with a method which checks if the given value is palindrome or not

public class Palindrome {
	  static boolean isPalindrome(String str) 
	    { 
	  
	        // Point to the beginning and the end of the string 
	        int i = 0, j = str.length() - 1; 
	      
	  
	        // While there are characters to compare to
	        while (i < j) { 
	  
	            // If there is a mismatch 
	            if (str.toLowerCase().charAt(i) != str.charAt(j)) 
	                return false; 
	  
	            // Increment first pointer and decrement the other 
	            i++; 
	            j--; 
	        } 
	  
	        // If no mismatch appears -> given string is a palindrome 
	        return true; 
	    } 
	  
	    // Driver code 
	    public static void main(String[] args) 
	    { 
	        String str = "loL"; 
	        System.out.print(str);
	  
	        if (isPalindrome(str)) 
	            System.out.print(" is palindrome"); 
	        else
	            System.out.print(" is not palindrome"); 
	    } 
}
