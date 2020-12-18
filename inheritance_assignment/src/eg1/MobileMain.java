package eg1;

public class MobileMain {

	public static final int M=90000;
	
	public static void main(String[] args) {
		MobileV3 v3=new MobileV3();
		System.out.println("Accessing props using v3 object");
		v3.socialApps();
		v3.browsing();
		v3.gaming();
		v3.calling();
		v3.message();
		System.out.println(v3.getClass());
		System.out.println(v3.hashCode());
		v3.displayMenu();
		
		final int x=10;
		//x=100;
		System.out.println(Integer.MAX_VALUE);
		

	}

}
