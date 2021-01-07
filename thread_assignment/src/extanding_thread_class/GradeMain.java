package extanding_thread_class;

public class GradeMain {
public static void main(String[] args) {
	System.out.println(Thread.currentThread()); // Thread[main,5,main]
	Thread g=Thread.currentThread();
	g.setName("mainGrade");
	System.out.println(g); // Thread[mainGrade,5,main]



	Grade g5= new Grade();
	g5.setName("excellent");
	g5.start(); //Thread[excellent,5,main]
	
	Grade g4= new Grade();
	g4.setName("good");
	g4.setPriority(4);
	g4.start(); //Thread[good,4,main]
	
	Grade g3= new Grade();
	g3.setName("fair");
	g3.setPriority(3);
	g3.start(); //Thread[good,3,main]
	
	Grade g2= new Grade();
	g2.setName("fail");
	g2.setPriority(2);
	g2.start(); //Thread[good,2,main]
	
	//priority number does not prioritize printing order
	
	
	//printing g (MainGrade) 4 times with pause 1500 milisec between each other
	
	for (int i = 0; i < 4; i++) {
		System.out.println("Printing from thread - "+Thread.currentThread().getName()+" value of i = "+(i+1));
		try {
			Thread.sleep(1500); //pause between rounds
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
}
