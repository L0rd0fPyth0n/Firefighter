package pt.iul.poo.firefight.starterpack.Plants;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;

public class Grass extends Plant  {
	
	public Grass(Point2D position) {
		super(position, 3, .15);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getName() {
		if(super.wasConsume()) return "burntgrass";
		return "grass";
	}


	
}
