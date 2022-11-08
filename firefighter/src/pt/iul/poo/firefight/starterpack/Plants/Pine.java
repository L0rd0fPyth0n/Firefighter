package pt.iul.poo.firefight.starterpack.Plants;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;

public class Pine extends Plant  {
	
	public Pine(Point2D position) {
		super(position,  10, .05);
		
	}

	@Override
	public String getName() {
		if(super.wasConsume()) return "burntpine";
		return "pine";
	}
}
