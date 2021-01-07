package synchronized_example;

public class Animal implements Runnable {
	private String head;
	private String body;
	private String tail;
	
	public Animal() {
		// TODO Auto-generated constructor stub
	}
	

	public Animal(String head, String body, String tail) {
		super();
		this.head = head;
		this.body = body;
		this.tail = tail;
		
		Thread t = new Thread(this);  //Add this line to constructor
		t.start();
	}


	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		this.head = head;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getTail() {
		return tail;
	}


	public void setTail(String tail) {
		this.tail = tail;
	}


	@Override
	public void run() {
	Factory.makeAnimal(this); //use this for current Animal
		
	}

}
