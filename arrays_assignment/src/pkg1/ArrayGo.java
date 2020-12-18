package pkg1;

import java.util.Arrays;

public class ArrayGo {
	
	public static void main(String[] args) {
		
		int ar[]= new int[] {4,344,66,3,33,9,40,15};
		System.out.println("Array :");
		System.out.println(Arrays.toString(ar));
		
		/* 1) Print the array in reverse order */
		
		System.out.println("\nThe array in reverse order:");
		for (int i = ar.length-1; i >= 0; i--) {
			System.out.print(ar[i]+" ");
		}
		
		/* 2)Delete specific element from an array */


		// Find an index of the element we would like to delete
		int el=344;
		int x = 0;
		int n = ar.length;
				
		for (int i = 0; i < ar.length; i++) {
			if(ar[i]==el) {
				x=i;
			}
		}
		
        // Print an array before deletion
		System.out.println("\n\nThe array before deletion :");
		for (int i = 0; i < n; i++) {
			System.out.print(ar[i]+" ");
		}
		
		// Delete element with found index (move all following elements) 
		for (int i = x; i < n-1; i++) {
			ar[i]=ar[i+1];
		}
		n--;
		
		//Print out an array after deleting element
		System.out.println("\n\nThe array after deletion of element 344:");
		for (int i = 0; i < n; i++) {
			System.out.print(ar[i]+" ");
		}
		
		/* 3)Find all the even numbers and put it in a new array and also find all odd numbers and put it in another array */
		
		//Find all even numbers
		System.out.println("\n\nAn array of even numbers : ");
		int arEv [] = new int[n];
		int j=0;
		
		for (int i = 0; i < n; i++) {
			if (ar[i]%2==0) {
				arEv[j]=ar[i];
				j++;
				}
			}
		System.out.println(Arrays.toString(arEv));	
		
		//Find all odd numbers
		System.out.println("\n\nAn array of odd numbers : ");
		int arOdd [] = new int[n];
		int k=0;

		
		for (int i = 0; i < n; i++) {
			if (ar[i]%2!=0) {
				arOdd[k]=ar[i];
				k++;
			}
		}
		System.out.println(Arrays.toString(arOdd));	
		
		
		/* 4)Find the sum of all even and odd numbers separately and print the max out of it.(max of sum of even vs odd) */
		
		// Find the sum of all even numbers
		System.out.println("\nThe sum of all even numbers :");
		for (int j1 = 0; j1 < n; j1++) {
			System.out.print(arEv[j1]+" + ");
		}
		
		int sumEv= Arrays.stream(arEv).sum();
		System.out.println("= " +sumEv);
		
		// Find the sum of all odd numbers
		System.out.println("\nThe sum of all odd numbers :");
		for (int k1 = 0; k1 < n; k1++) {
			System.out.print(arOdd[k1]+" + ");
		}
		
		int sumOdd= Arrays.stream(arOdd).sum();
		System.out.println("= "+sumOdd);
		
		// Compare sums and print the max out of it.(max of sum of even vs odd)
		if (sumEv > sumOdd){
			System.out.println("Sum of even numbers is bigger that odds by "+(sumEv-sumOdd));
		}
		else if (sumEv < sumOdd){
			System.out.println("Sum of odd numbers is bigger that even by "+(sumOdd-sumEv));
		}
		else {System.out.println("Sum of odd numbers is equel to sum of even numbers");
		}
		
	}

}
//Tasks
/*
*1)Print the array in reverse order
*2)Delete specific element from an array
*3)Find all the even numbers and put it in a new array and also find all odd numbers and put it in another array
*4)Find the sum of all even and odd numbers seperately and print the max out of it.(max of sum of even vs odd)
*5)Find the minimum and the maximum element in an array
*6)Find all Palindrome numbers in an array 
*/