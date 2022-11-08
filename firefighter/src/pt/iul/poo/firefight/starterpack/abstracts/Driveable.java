package pt.iul.poo.firefight.starterpack.abstracts;


import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Fireman;
import pt.iul.poo.firefight.starterpack.GameEngine;

public abstract class Driveable extends Moveable {
	public Driveable(Point2D position) {
		super(position);
	}

	public void Enter(Fireman fireman) {
		fireman.vanish();
		Moveable.setMovingThis(this);
	}

	public void Leave() {
		Fireman fireman = GameEngine.getInstance().getFireman();
		fireman.setPosition(getPosition());
		ImageMatrixGUI.getInstance().addImage(fireman);
		Moveable.setMovingThis(fireman);
	}

	

}
