package operators;

// ArithmeticOperators

public class ArithmeticOperators{
	
	public static void main (String[] args) { 
	
	/*
	 * +	Addition Operator	
	 * '+' Operator adds two values. 
	 * This operator works on integer, float and character variables.	20 + 10 return 20
	 */
	int a = 25;
	int b = 10;
	int c = a+b;
	System.out.println(a+" + "+b+" = "+c);
	 
	 /* -	Subtraction Operator	
	 * '-' Operator produces a result after subtracting two values. 
	 * It also works on integer, float, character variables.	20 - 10 returns 10
	 */

	int d = a-b;
	System.out.println(a+" - "+b+" = "+d);
	 
	 /*  *	Multiplication Operator	
	 *  '*' Operator produces result after multiplication of operands.	20 * 10 returns 200
	 */

	int e = a*b;
	System.out.println(a+" * "+b+" = "+e);
	  
	  /*   /	Division Operator	
	 *  '/' Operator produces result of division of first operand by second.	
	 */
	int f = a/b;
	System.out.println(a+" / "+b+" = "+f);
	  
	/*  
	 *  &	Modulus Operator	
	 *  '%' Operator generates the remainder after integer division.	25 % 10 returns 5
	 */
	
	int g = a/b;
	System.out.println(a+" % "+b+" = "+g);
}
}
