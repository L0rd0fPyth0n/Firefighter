package pt.iul.poo.firefight.starterpack.interfaces;

import pt.iul.ista.poo.utils.Point2D;

public interface Spawnable {

	public void spam(Point2D pos);
	public boolean canSpamHere(Point2D pos);
}
