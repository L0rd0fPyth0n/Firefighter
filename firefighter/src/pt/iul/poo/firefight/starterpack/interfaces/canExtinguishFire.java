package pt.iul.poo.firefight.starterpack.interfaces;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Fire;

public interface canExtinguishFire {

	public void extinguish(Fire f);
	
	public void showWater(Point2D where);
	
}
