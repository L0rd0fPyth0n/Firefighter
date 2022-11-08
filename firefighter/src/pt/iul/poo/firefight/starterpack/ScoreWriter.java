package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ScoreWriter {
	private final int level;
	private final int score;
	List<Player> players;

	public ScoreWriter(int level, int score) {
		this.level = level;
		this.score = score;
		this.players = new LinkedList<>();
	}

	// file syntax:
	// username score
	public void getPlayers() {
		File scoreBoard = new File("scoreBoard\\level" + level);
		Scanner s;
		try {
			s = new Scanner(scoreBoard);
			while (s.hasNextLine()) {
				String username = s.next();
				int points = s.nextInt();
				players.add(new Player(points, username));
				s.nextLine();
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getCurrentPlayer() {
		Scanner myObj = new Scanner(System.in);
		String str = myObj.nextLine();
		players.add(new Player(score, str));
		myObj.close();

	}

	public void writeTopFive() {
		File scoreBoard = new File("scoreBoard\\level" + level);
		PrintWriter pw;
		try {
			pw = new PrintWriter(scoreBoard);
			pw.print(""); // Para eliminar conteudo do ficheiro
			int cont = 1;
			for (Player p : players) {
				if (cont >= 5)
					break;
				pw.println(p);
				cont++;
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveScore() {
		getPlayers();
		getCurrentPlayer();
		players.sort((Player n1, Player n2) -> n2.getScore() - n1.getScore());
		writeTopFive();
	}
}
