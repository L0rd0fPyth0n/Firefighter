package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.Waters.WaterInPosition;
import pt.iul.poo.firefight.starterpack.abstracts.Moveable;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;
import pt.iul.poo.firefight.starterpack.abstracts.Water;
import pt.iul.poo.firefight.starterpack.interfaces.Updateable;
import pt.iul.poo.firefight.starterpack.interfaces.Vanishable;
import pt.iul.poo.firefight.starterpack.interfaces.canExtinguishFire;

public class Airplane extends Moveable implements Updateable, ImageTile, canExtinguishFire, Vanishable {
	public Airplane() {
		super(hasMoreFires());
		ImageMatrixGUI.getInstance().addImage(this);
		Fire f = Fire.getFire(getPosition());
		if (f != null) {
			this.showWater(getPosition());
			this.extinguish(f);
		}
	}

	@Override
	public void move(Direction d) {
		super.move(d);
		Fire f = Fire.getFire(getPosition());
		if (f != null) {
			this.showWater(getPosition());
			this.extinguish(f);
		}
	}

	@Override
	public String getName() {
		return "plane";
	}

	@Override
	public int getLayer() {
		return 3;
	}

	public static Point2D hasMoreFires() {
		int[] firesInCol = new int[GameEngine.GRID_WIDTH];
		for (Point2D p : Fire.getFIREMAP().keySet()) {
			firesInCol[p.getX()]++;
		}

		int largest = 0;
		for (int i = 1; i < firesInCol.length; i++) {
			if (firesInCol[i] > firesInCol[largest])
				largest = i;
		}
		return new Point2D(largest, GameEngine.GRID_HEIGHT );
	}

	public boolean canMoveTo(Point2D p) {
		return true;
	}

	@Override
	public void extinguish(Fire f) {
		f.vanish();
		Plant.getPlant(f.getPosition()).PutFireOut();
	}

	@Override
	public void vanish() {
		ImageMatrixGUI.getInstance().removeImage(this);
		GameEngine.getInstance().removeGameElement(this);
	}

	@Override
	public void update() {
		Direction up = Direction.UP;
		for (int i = 0; i < 2; i++) {
			move(up);
			if (getPosition().getY() <= -1)
				vanish();
		}
	}

	@Override
	public void showWater(Point2D where) {
		Water w = new WaterInPosition(where);
	}

}
