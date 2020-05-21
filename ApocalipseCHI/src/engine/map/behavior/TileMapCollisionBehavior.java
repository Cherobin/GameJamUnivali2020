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
		this.name = "tile_map_collision";
		this.layerIndex = layerIndex;
		this.map = map;
		layerIndex = map.wallID; // << collision layer
	}
 
	@Override
	public void update(GameObject e, float diffTime) {
		if (e != null) {
			GameEntity ge = (GameEntity) e;
			ge.inCollider = false;
			boolean colisionCenter = map.colision((int) ge.position.x, (int) ge.position.y);

			if (colisionCenter) {
				switch (ge.getName()) {
				case "fire":
					ge.alive = false;
					ge.inCollider = true; 
					break;
				case "Enemy": 
					ge.inCollider = true; 
					break;
				default:
					ge.inCollider = true; 
					break;
				} 
			} 

		}
	}
}