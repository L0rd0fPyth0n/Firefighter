package pt.iul.poo.firefight.starterpack.abstracts;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements  ImageTile{
private Point2D position;

public GameElement(Point2D position) {
	this.position = position;
}
public void setPosition(Point2D position) { 
	this.position = position;;
}
public Point2D getPosition() {
	return position;
}


}
