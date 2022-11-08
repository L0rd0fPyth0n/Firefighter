package pt.iul.poo.firefight.starterpack.abstracts;

import java.util.LinkedHashMap;
import java.util.Map;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Fire;
import pt.iul.poo.firefight.starterpack.GameEngine;
import pt.iul.poo.firefight.starterpack.interfaces.Burnable;

public abstract class Plant extends GameElement implements  ImageTile, Burnable {
	private static Map<Point2D, Plant> PLANTMAP = new LinkedHashMap<>();
	private boolean isBurning;
	private boolean wasConsume;
	private int turnsLeftToBurn;
	private double PROBOFBURNIMG;

	public int getTurnsLeftToBurn() {
		return turnsLeftToBurn;
	}

	public static void removePlant(Plant p) {
		PLANTMAP.remove(p);
	}

	public static void addPlant(Plant p) {
		PLANTMAP.put(p.getPosition(), p);
	}

	public static Plant getPlant(Point2D p) {
		return PLANTMAP.get(p);
	}

	public Plant(Point2D position, int turnsLeftToBurn, double PROBOFBURNIMG) {
		super(position);
		this.isBurning = false;
		this.turnsLeftToBurn = turnsLeftToBurn;
		this.PROBOFBURNIMG = PROBOFBURNIMG;
		this.wasConsume = false;
	}

	public double getPROBOFBURNIMG() {
		return PROBOFBURNIMG;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override // We didn't
	public void StartAfire() {
		if (!this.isBurning) {
			this.isBurning = true;
			Fire.startAFire(this.getPosition());
		}

	}
	public boolean wasConsume(){
	return wasConsume;	
	}

	

	public static Map<Point2D, Plant> getPLANTMAP() {
		return PLANTMAP;
	}

	@Override
	public boolean willItBurn() {
		return Math.random() <= this.getPROBOFBURNIMG();
	}

	@Override
	public void burnDown() {
		ImageMatrixGUI.getInstance().removeImage(this);
		this.isBurning = false;
		wasConsume = true;
		this.PROBOFBURNIMG = 0;
		ImageMatrixGUI.getInstance().addImage(this);
		Fire.getFire(this.getPosition()).vanish();
		GameEngine.getInstance().loseScore();

	}

	@Override
	public boolean isBurning() {
		return this.isBurning;
	}
	public void PutFireOut() {
		this.isBurning = false;

	}

}
