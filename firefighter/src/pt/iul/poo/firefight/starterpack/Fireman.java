package pt.iul.poo.firefight.starterpack;
import java.util.Map;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Waters.WaterDown;
import pt.iul.poo.firefight.starterpack.Waters.WaterLeft;
import pt.iul.poo.firefight.starterpack.Waters.WaterRight;
import pt.iul.poo.firefight.starterpack.Waters.WaterUp;
import pt.iul.poo.firefight.starterpack.abstracts.Driveable;
import pt.iul.poo.firefight.starterpack.abstracts.GameElement;
import pt.iul.poo.firefight.starterpack.abstracts.Moveable;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;
import pt.iul.poo.firefight.starterpack.abstracts.Water;
import pt.iul.poo.firefight.starterpack.interfaces.Vanishable;
import pt.iul.poo.firefight.starterpack.interfaces.canExtinguishFire;

public class Fireman extends Moveable implements Vanishable, canExtinguishFire {

	public Fireman(Point2D position) {
		super(position);
	}

	@Override
	public void move(Direction d) {
		Point2D newPos = getPosition().plus(d.asVector());
		for (GameElement ge : GameEngine.getInstance().getGameElements()) {
			if (ge instanceof Driveable && ge.getPosition().equals(newPos)) 
				{
			((Driveable) ge).Enter(this);
				}
		}
		Fire f = Fire.getFire(newPos);
		if (f != null) {
			showWater(newPos);
			this.extinguish(f);
		} else
			super.move(d);

	}

	public boolean isThereFire(Map<Point2D, Fire> fireMap, Point2D pos) {
		return fireMap.containsKey(pos);

	}

	@Override
	public void setPosition(Point2D position) {
		super.setPosition(position);
	}

	@Override
	public boolean canMoveTo(Point2D p) {
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

	// Metodos de ImageTile
	@Override
	public String getName() {
		return "fireman";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void vanish() {
		ImageMatrixGUI.getInstance().removeImage(this);
		setPosition(new Point2D(-2,-2));
	}

	@Override
	public void extinguish(Fire f) {
		f.vanish();
		Plant.getPlant(f.getPosition()).PutFireOut();
		GameEngine.getInstance().addScore();
	}

	@Override
	public void showWater(Point2D where) {
		Water w;
		int key = ImageMatrixGUI.getInstance().keyPressed();
		Direction dir = null;
		if (Direction.isDirection(key))
			dir = Direction.directionFor(ImageMatrixGUI.getInstance().keyPressed());
		switch (dir) {
		case UP:
			w = new WaterUp(where);
			break;
		case DOWN:
			w = new WaterDown(where);
			break;
		case LEFT:
			w = new WaterLeft(where);
			break;
		case RIGHT:
			w = new WaterRight(where);
			break;
		default:
			throw new IllegalStateException();
		}
		
	}

}
