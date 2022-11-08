package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Plants.NoPlant;
import pt.iul.poo.firefight.starterpack.abstracts.Driveable;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;

public class Bulldozer extends Driveable {
	String name;
	public Bulldozer(Point2D position) {
		super(position);
		name = "bulldozer";		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	
	@Override
	public void move(Direction d) {
		super.move(d);
		animateMovement(d);
		destroyVegetation();
	}

	public void animateMovement(Direction d) {
		ImageMatrixGUI.getInstance().removeImage(this);
		switch (d) {
		case UP:
			name = "bulldozer_up";
			break;
		case DOWN:
			name = "bulldozer_down";
			break;

		case LEFT:
			name = "bulldozer_left";
			break;
		case RIGHT:
			name = "bulldozer_right";
			break;
		}
		ImageMatrixGUI.getInstance().addImage(this);
	}

	public void destroyVegetation() {
		Point2D position = getPosition();
		if (!Plant.getPlant(position).wasConsume()) {
			ImageMatrixGUI.getInstance().removeImage(Plant.getPlant(position));
			Plant.addPlant(new NoPlant(position));
			ImageMatrixGUI.getInstance().addImage(Plant.getPlant(position));
		}
	}

	@Override
	public boolean canMoveTo(Point2D p) {
		Fire f = Fire.getFire(p);
		if (f != null)
			return false;
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;
	}

	
}
