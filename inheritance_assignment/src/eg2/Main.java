package eg2;

public class Main {

	public static void main(String[] args) {
	
		Project p1=new Project(9000, "Abc Corp", "Abc Corp Some Client");
		
		Address permAd1=new Address(1,7867,"Creek","Madison","WI",89554);
		Address prAd1=new Address(2,4526,"Clyborn","Chicago","IL",60615);
		
		Employee e1=new  Employee(100, "Sachin", "Manager", 23333.44,p1,permAd1,prAd1);
		System.out.println("e1");
		System.out.println(e1);
		
		Address permAd2=new Address(4,8044,"Western","Hammond","IN",35687);
		
		Employee e2=new Employee(101, "Richard", "Associate", 2000.33,p1,permAd2,prAd1);
		System.out.println("e2");
		System.out.println(e2);
		
		Project p2=new Project(9001, "SMS", "Revature");
		
		Address permAd3=new Address(8,7797,"Velvet","Vail","CO",78944);
		Address prAd3=new Address(7,3512,"Wilson","Chicago","IL",60640);
		
		Employee e3=new Employee(102, "Tushar", "Manager", 33000.33, p2,permAd3,prAd3);
		System.out.println("e3");
		System.out.println(e3);
		
//Complete Address, Player and Team
		
		//Create new teams 
		Team t1 = new Team(101, "Blue", "Stive");
		System.out.println("t1");
		System.out.println(t1);
		
		Team t2 = new Team(102, "Red", "Mike");
		System.out.println("t2");
		System.out.println(t2);
		
		//Create new players
		
		Player pl1=new Player(555,"Luke",8076.66,t1);
		System.out.println("pl1");
		System.out.println(pl1);
		
		Player pl2=new Player(556,"Mike",8077.97,t1);
		System.out.println("pl2");
		System.out.println(pl2);
		
		Player pl3=new Player(557,"Dave",8000.21,t2);
		System.out.println("pl3");
		System.out.println(pl3);
		
		Player pl4=new Player(558,"Alex",7899.09,t2);
		System.out.println("pl4");
		System.out.println(pl4);
	}

} 
