package eg2;

public class Player extends Person{
	
	private double rating;
	private Team team;
	
	// Constructor provided by compiler
	
	public Player() {
		super();

	}
	
	//Constructor including fields of Player and fields of super class Person
	
	public Player(int id, String name, double rating, Team team) {
		super(id, name);
		this.rating = rating;
		this.team = team;
	}
	
	//Getter and Setter
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Player [rating=" + rating + ", team=" + team + ", toString()="
				+ super.toString() + "]";
	}
	

}
