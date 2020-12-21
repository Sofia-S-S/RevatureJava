package pkg1;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CollectionsGo {

	public static void main(String[] args) {
		List<Integer> ar=new ArrayList<>();
		ar.add(404);//insert
		ar.add(344);//insert
		ar.add(66);//insert
		ar.add(3);//insert
		ar.add(303);//insert
		ar.add(9);//insert
		ar.add(404);//insert
		ar.add(15);//insert
		System.out.println("ar = "+ar);
		
		/* 1) Print the array in reverse order */
		
		System.out.println("\nThe array list in reverse order:");
		for (int i = ar.size()-1; i >= 0; i--) {
			System.out.print(ar.get(i)+" ");
		}
		
		/* 2)Delete specific element from an array */
		
		ar.remove(new Integer(344));
		System.out.println("\n\nThe array after deletion of element 344:");
		System.out.println("ar = "+ar);

		/* 3)Find all the even numbers and put it in a new array and also find all odd numbers and put it in another array */
		
		//Find all even numbers
		System.out.println("\n\nAn array of even numbers : ");
		List<Integer> arEv=new ArrayList<>();
		
		for (int i = 0; i < ar.size(); i++) {
			if (ar.get(i)%2==0) {
				arEv.add(ar.get(i));
				}
			}
		System.out.println(arEv);	
		
		//Find all odd numbers
		System.out.println("\nAn array of odd numbers : ");
		List<Integer> arOdd=new ArrayList<>();
		
		for (int i = 0; i < ar.size(); i++) {
			if (ar.get(i)%2!=0) {
				arOdd.add(ar.get(i));
			}
		}
		System.out.println(arOdd);	
		
		/* 4)Find the sum of all even and odd numbers separately and print the max out of it.(max of sum of even vs odd) */
		
		// Find the sum of all even numbers
		System.out.println("\nThe sum of all even numbers :");
		for (int j1 = 0; j1 < arEv.size(); j1++) {
			System.out.print(arEv.get(j1)+" + ");
		}
		
		int sumEv= arEv.stream().mapToInt(a -> a).sum();
		System.out.println("= " +sumEv);
		
		// Find the sum of all odd numbers
		System.out.println("\nThe sum of all odd numbers :");
		for (int k1 = 0; k1 < arOdd.size(); k1++) {
			System.out.print(arOdd.get(k1)+" + ");
		}
		
		int sumOdd= arOdd.stream().mapToInt(a -> a).sum();
		System.out.println("= "+sumOdd);
		
		// Compare sums and print the max out of it.(max of sum of even vs odd)
		if (sumEv > sumOdd){
			System.out.println("\nSum of even numbers is bigger that odds by "+(sumEv-sumOdd));
		}
		else if (sumEv < sumOdd){
			System.out.println("\nSum of odd numbers is bigger that even by "+(sumOdd-sumEv));
		}
		else {System.out.println("\nSum of odd numbers is equel to sum of even numbers");
		}
		
		/* 5)Find the minimum and the maximum element in an array */
		
		int max = Collections.max(ar);
		System.out.println("\nThe maximum element in an array equels "+max);
		
		
		int min = Collections.min(ar);
		System.out.println("\nThe minimum element in an array equels "+min);
		
		/* 6)Find all palindromic numbers in an array */
		
		System.out.println("\nPalindrome numbers in array : ");
       		
		for (int p = 0; p < arOdd.size(); p++) {
			if (ar.get(p) == Integer.reverse(ar.get(p)));
	            System.out.print(ar.get(p) + " ");
		}
		
	}
	
	
}
