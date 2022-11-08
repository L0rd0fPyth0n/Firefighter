package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.lang.IllegalStateException;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Plants.Eucalyptus;
import pt.iul.poo.firefight.starterpack.Plants.Grass;
import pt.iul.poo.firefight.starterpack.Plants.NoPlant;
import pt.iul.poo.firefight.starterpack.Plants.Pine;
import pt.iul.poo.firefight.starterpack.abstracts.Driveable;
import pt.iul.poo.firefight.starterpack.abstracts.GameElement;
import pt.iul.poo.firefight.starterpack.abstracts.Moveable;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;
import pt.iul.poo.firefight.starterpack.abstracts.Water;
import pt.iul.poo.firefight.starterpack.interfaces.Updateable;

import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<ImageTile> tileList; // Lista de imagens
	private List<GameElement> gameElements;
	private Fireman fireman;
	private int score;
	int level;
	
	public void addScore(){
	score += 5;	
	}
	public void loseScore(){
	score -= 5;	
		}
	private static GameEngine INSTANCE;

	public Fireman getFireman() {
		return fireman;
	}

	public void setFireman(Fireman fireman) {
		this.fireman = fireman;
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {
		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
	}

	public void removeGameElement(GameElement ge) {
		gameElements.remove(ge);
	}

	public void changeGameElement(GameElement ge) {
		gameElements.remove(ge);
	}

	public List<GameElement> getGameElements() {
		return gameElements;
	}

	public void addGameElment(GameElement ge) {
		gameElements.add(ge);
	}

	@Override
	public void update(Observed source) {
		int key = ImageMatrixGUI.getInstance().keyPressed();
		if (KeyEvent.VK_P == key) {
			gameElements.add(new Airplane());
		}
		Moveable m = Moveable.getMovingThis();

		if (Direction.isDirection(key)) {
			Direction d = Direction.directionFor(key);
			m.move(d);
		} else {
			if (KeyEvent.VK_ENTER == key)
				((Driveable) m).Leave();
		}
		List<Updateable> ups = new LinkedList<>();
		ups.addAll(Fire.getFIREMAP().values());
		ups.addAll(Water.getWaters());
		for (GameElement ge : gameElements) {
			if (ge instanceof Airplane)		ups.add((Updateable) ge);
		}
		for (Updateable up : ups) {
			up.update();
		}
		gui.setStatusMessage("Score : " + score);
		gui.update();
		if (ended()) {
			gui.clearImages();
			start(++level);
		}
	}

	public boolean ended() {
		return Fire.getFIREMAP().isEmpty();
	}
	// Criacao dos objetos e envio das imagens para GUI
	public void start(int level) {
		gameElements = new ArrayList<>();
		tileList = new ArrayList<>();
		this.score = 0;
		this.level = level;
		gui.go(); // 4. lancar a GUI
		gui.setStatusMessage("Score : " + score);
		try {
			createTerrain(level); // criar mapa do terreno
			createMoreStuff(level); // criar mais objetos (bombeiro, fogo,...)
		} catch (FileNotFoundException e) {
			System.out.println("Map not found");
		}
		sendImagesToGUI();
		gui.update();
	}

	private void createTerrain(int level) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("levels\\level" + level + ".txt"));
		for (int i = 0; i < GRID_HEIGHT; i++) {
			if (scan.hasNextLine())
				putStuffInLine(scan.nextLine(), i + 1);
		}
		scan.close();

	}

	public void putStuffInLine(String str, int line) {
		int x = 0;
		for (char c : str.toCharArray()) {
			Point2D p = new Point2D(x++, line - 1);
			Plant plant;
			switch (c) {
			case 'p':
				plant = new Pine(p);
				Plant.addPlant(plant);
				break;
			case 'm':
				plant = new Grass(p);
				Plant.addPlant(plant);
				break;
			case '_':
				plant = new NoPlant(p);

				Plant.addPlant(plant);
				break;
			case 'e':
				plant = new Eucalyptus(p);

				Plant.addPlant(plant);
				break;
			default:
				throw new NullPointerException("Null plant");
			}
			tileList.add(plant);
		}

	}

	// Criacao de mais objetos - neste exemplo e' um bombeiro e dois fogos
	private void createMoreStuff(int level) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("levels\\level" + level + ".txt"));
		for (int i = 0; i < GRID_HEIGHT; i++) {
			if (scan.hasNextLine())
				scan.nextLine();
		}
		while (scan.hasNextLine()) {
			String[] line = scan.nextLine().split(" ");
			int x = Integer.parseInt(line[1]);
			int y = Integer.parseInt(line[2]);
			Point2D p = new Point2D(x, y);
			switch (line[0]) {
			case "Fireman":
				if (Fire.getFire(p) == null) {
					Fireman f = new Fireman(p);
					Moveable.setMovingThis(f);
					fireman = f;
					tileList.add(f);
					gameElements.add(f);
				}
				break;
			case "Fire":
				Plant.getPlant(p).StartAfire();

				break;
			case "Bulldozer":
				if (Fire.getFire(p) == null) {
					Bulldozer b = new Bulldozer(p);
					gameElements.add(b);
					tileList.add(b);
				}
				break;
			default:
				throw new IllegalStateException();
			}

		}

		scan.close();
	}

	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
