package pt.iul.poo.firefight.starterpack.abstracts;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Moveable extends GameElement {

	private static Moveable movingThis;
	
	public static Moveable getMovingThis() {
		return movingThis;
	}


	public static void setMovingThis(Moveable movingThis) {
		Moveable.movingThis = movingThis;
	}


	public Moveable(Point2D position) {
		super(position);
	}
	

	public void move(Direction d) {
		Point2D newPos = getPosition().plus(d.asVector());
		if (canMoveTo(newPos)) 	this.setPosition(newPos);
		}


	public abstract boolean canMoveTo(Point2D p) ;

}
