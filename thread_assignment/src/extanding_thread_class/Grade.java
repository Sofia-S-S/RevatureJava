package extanding_thread_class;

public class Grade extends Thread{
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread());
		
		//printing everything(g1 to g5) 4 times with pause 1500 milisec between each round
		
		for (int i = 0; i < 4; i++) {
			System.out.println("Printing from thread - "+Thread.currentThread().getName()+" value of i = "+(i+1));
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
  
}
