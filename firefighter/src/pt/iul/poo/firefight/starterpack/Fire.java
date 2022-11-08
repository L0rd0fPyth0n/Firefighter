package pt.iul.poo.firefight.starterpack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.abstracts.Driveable;
import pt.iul.poo.firefight.starterpack.abstracts.GameElement;
import pt.iul.poo.firefight.starterpack.abstracts.Plant;
import pt.iul.poo.firefight.starterpack.abstracts.Water;
import pt.iul.poo.firefight.starterpack.interfaces.Updateable;
import pt.iul.poo.firefight.starterpack.interfaces.Vanishable;

public class Fire extends GameElement implements Updateable, ImageTile, Vanishable {
	private static Map<Point2D, Fire> FIREMAP = new HashMap<>();
	private int turnsToBurnout;

	public static void removeFire(Fire f) {
		FIREMAP.remove(f);
	}

	public static void addFire(Fire f) {
		FIREMAP.put(f.getPosition(), f);
	}

	public static Map<Point2D, Fire> getFIREMAP() {
		return FIREMAP;
	}

	public static Fire getFire(Point2D p) {
		return FIREMAP.get(p);
	}

	private static boolean hasNothing(Point2D pos) {
		List<GameElement> firemansAndDriveables = GameEngine.getInstance().getGameElements();
		firemansAndDriveables.removeIf(g -> !(g instanceof Driveable || g instanceof Fireman));
		for (GameElement ge : firemansAndDriveables) {
			if (ge.getPosition().equals(pos))
				return false;
		}
		for (Updateable w : Water.getWaters()) {
			if (w instanceof Water) {
				if (((Water) w).getPosition().equals(pos))
					return false;
			}
		}
		return true;
	}

//						we didn't
	public static void startAFire(Point2D position) {
		if (hasNothing(position)) {
			Fire f = new Fire(position, Plant.getPlant(position).getTurnsLeftToBurn());
			addFire(f);
			ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
			gui.addImage(f);
		}
	}

	private Fire(Point2D position, int turnsToBurnout) {
		super(position);
		this.turnsToBurnout = turnsToBurnout;

	}

	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void vanish() {
		ImageMatrixGUI.getInstance().removeImage(this);
		Fire.FIREMAP.remove(this.getPosition());
	}

	@Override
	public void update() {
		spreadFire();
		if (turnsToBurnout-- <= 1) {
			Plant.getPlant(getPosition()).burnDown();
			removeFire(this);
			vanish();
		}
	}

	public void spreadFire() {
		List<Point2D> points = this.getPosition().getNeighbourhoodPoints();
		for (Point2D point : points) {
			if (point.getX() >= 0 && point.getY() >= 0 && point.getX() < GameEngine.GRID_WIDTH
					&& point.getY() < GameEngine.GRID_HEIGHT) {
				Plant plant = Plant.getPlant(point);
				if (plant.willItBurn())
					plant.StartAfire();
			}
		}
	}
}
