package engine.entities; 

import java.awt.Color;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMapCollisionBehavior;
import engine.utils.Vector2D;
import tiled.core.Map;
 
public class Player extends GameEntity implements GameObject {

	public Player(String name) {
		super(name);
	}

	public Player(String name, Vector2D position, int width, int height, float rotation, Map map) {
		super(name, position, width, height, rotation);

		color = Color.GREEN;
		offset.x = -width / 2;
		offset.y = -height;
		initializeComponents(map); 
	}

	private void initializeComponents(Map map) { 
		addComponent(new TileMapCollisionBehavior(map, 1));
	}

}
