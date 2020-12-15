
public class Palindrome {
	  static boolean isPalindrome(String str) 
	    { 
	  
	        // We are pointing to the beginning and the end of the string 
	        int i = 0, j = str.length() - 1; 
	  
	        // While there are characters to compare to
	        while (i < j) { 
	  
	            // If there is a mismatch 
	            if (str.charAt(i) != str.charAt(j)) 
	                return false; 
	  
	            // Increment first pointer and decrement the other 
	            i++; 
	            j--; 
	        } 
	  
	        // Given string is a palindrome 
	        return true; 
	    } 
	  
	    // Driver code 
	    public static void main(String[] args) 
	    { 
	        String str = "lol"; 
	  
	        if (isPalindrome(str)) 
	            System.out.print("Yes, it's palindrome"); 
	        else
	            System.out.print("No, it's not palindrome"); 
	    } 
}
