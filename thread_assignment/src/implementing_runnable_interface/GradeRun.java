package implementing_runnable_interface;

public class GradeRun implements Runnable {

	@Override
	public void run() {
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
