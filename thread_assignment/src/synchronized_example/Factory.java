package synchronized_example;

public class Factory {
	
	public synchronized static void makeAnimal(Animal animal) {   //synchronized will start making new animal 
																	//only after previous one is finished
		System.out.println(animal.getHead());
		System.out.println(animal.getBody());
		System.out.println(animal.getTail());
	}

}
