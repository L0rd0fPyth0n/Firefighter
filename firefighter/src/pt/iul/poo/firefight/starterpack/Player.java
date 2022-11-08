package pt.iul.poo.firefight.starterpack;

public class Player {
	int score;
	String username;

	public Player(int score, String username) {
		this.score = score;
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username + " " + score;
	}

}

