package engine.map.behavior;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMap;

public class TileMapCollisionBehavior extends AbstractComponent implements Component {

	protected TileMap map;

	protected int layerIndex;

	public TileMapCollisionBehavior(TileMap map, int layerIndex) {
		this.layerIndex = layerIndex;
		this.map = map;
		initialize(null);
	}

	@Override
	public void initialize(GameObject e) {
		layerIndex = map.wallID; // << collision layer
	}

	@Override
	public void update(GameObject e, float diffTime) {
		if (e != null) {
			GameEntity ge = (GameEntity) e;
			boolean colisionCenter = map.colision((int) ge.position.x, (int) ge.position.y);

			if (colisionCenter) {
				ge.position.set(ge.oldPosition);
				ge.speed.x = ge.speed.y = 0;
			}

		}
	}
}