package eg1;

public class MobileV3 extends MobileV2 {

	public void socialApps() {
		System.out.println("Social apps from V3");
	}
	public MobileV3() {
		// TODO Auto-generated constructor stub
		System.out.println("V3");
	}
	
	@Override
	public void displayMenu() {
		System.out.println("Menu in V3 updated to GRID view");
	}
}
//you cannot override static methods, constructors, 
//final methods and private of parent
//to perform override there should must be parent and child relationship