package engine.map.behavior;

import java.awt.Color;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.base.entities.GameEntity.GameEntityType;
import engine.core.game.CanvasGame;
import engine.entities.Faisca;
import engine.map.TileMap;
import engine.utils.Vector2D;

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
				switch (ge.type) {
				case  FIRE:
					ge.alive = false;
					ge.inCollider = true; 
					CanvasGame.sortedEntities.add( 
					new Faisca("faica " + ge.getName(), new Vector2D(ge.position.x, ge.position.y), new Vector2D(ge.speed.x, ge.speed.y), ge.rotation, 4, 8));
					break;
				case ENEMY: 
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