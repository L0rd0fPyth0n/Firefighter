
package pt.iul.poo.firefight.starterpack.Plants;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;

public class Eucalyptus extends Plant{
	
	public Eucalyptus(Point2D position) {
		super(position,  5, 0.1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		if(super.wasConsume()) return "burnteucaliptus";
		return "eucaliptus";
	}

	

	
}