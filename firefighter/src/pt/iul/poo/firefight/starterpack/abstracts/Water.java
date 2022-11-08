package pt.iul.poo.firefight.starterpack.abstracts;

import java.util.LinkedList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.interfaces.Updateable;
import pt.iul.poo.firefight.starterpack.interfaces.Vanishable;


public abstract class Water  implements ImageTile, Vanishable, Updateable{
	private String fileName;
	private Point2D point;
	private static List<Updateable> waters =  new LinkedList<>();
	private int counter = 1;
	@Override
	public void update() {
		if(counter-- <= 0 ) vanish();		
	}

	public static List<Updateable> getWaters() {
		return waters;
	}
	
	public Water(String fileName, Point2D point) {
		this.fileName = fileName;
		this.point = point;		
		ImageMatrixGUI.getInstance().addImage(this);
		Water.getWaters().add(this);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	@Override
	public Point2D getPosition() {
		return point;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public void vanish(){
		ImageMatrixGUI.getInstance().removeImage(this);
		waters.remove(this);
	}
	
	
}
