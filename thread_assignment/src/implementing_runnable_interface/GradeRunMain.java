package implementing_runnable_interface;

public class GradeRunMain {
	   
      public static void main(String[] args) {
    	  
    	  GradeRun r5 = new GradeRun();
    	  Thread g5 = new Thread(r5,"excellent");
    	  
    	  GradeRun r4 = new GradeRun();
    	  Thread g4 = new Thread(r4,"good");
    	  
    	  GradeRun r3 = new GradeRun();
    	  Thread g3 = new Thread(r3,"fair");
    	  
    	  GradeRun r2 = new GradeRun();
    	  Thread g2 = new Thread(r2,"fail");
    	  
    	  
//    	  g2.setDaemon(true);
//    	  g3.setDaemon(true);
//    	  g4.setDaemon(true); 
//    	  g5.setDaemon(true); 
    	
        	  
    	  g5.start();
    	  g4.start();
    	  g3.start();
    	  g2.start();
    	  
  		try {  //Main will end after everything printed out

			g2.join();
			g3.join();
			g4.join();
			g5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
  		
  		// all 1st of grades, 2nd...// then 1,2,3,4 of main // then main ends
		for (int i = 0; i < 4; i++) {
		System.out.println("Printing from thread - "+Thread.currentThread().getName()+" value of i = "+(i+1));
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println("Main ends here");
}
		
	
}
