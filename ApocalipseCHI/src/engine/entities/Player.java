package engine.entities; 

import java.awt.Color;

import com.sun.prism.paint.Stop;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Direction;
import engine.utils.Vector2D; 
import tiled.core.Map;
 
public class Player extends GameEntity implements GameObject {

	public Player(String name) {
		super(name);
	}

	public Player(String name, Vector2D position, int width, int height, float rotation, Map map) {
		super(name, position, width, height, rotation);

		direction = Direction.DOWN;
		color = Color.GREEN;
		offset.x = -width / 2;
		offset.y = -height;
		speed.x = speed.y = 100;
		initializeComponents(map); 
	}

	private void initializeComponents(Map map) { 
		addComponent(new TileMapCollisionBehavior(map, 1));
	}
	
	@Override
	public void update(float dt) { 
		super.update(dt);
		
		position.x += speed.x * dt/1000f; 
 
		 switch (direction) {
		case STOP:
			speed.x = speed.y = 0;
			break;

		default:
			break;
		} 
		 
	}
}
