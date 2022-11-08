package pt.iul.poo.firefight.starterpack.Plants;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;

public class NoPlant extends Plant{
	
	public NoPlant(Point2D position) {
		super(position,  0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "land";
	}
	@Override	//We didn't
	public void StartAfire() {
		return;
	}
	
}
