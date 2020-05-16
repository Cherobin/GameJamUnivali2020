package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Direction;
import engine.utils.Vector2D;
import tiled.core.Map;

public class Player extends GameEntity implements GameObject {

	private int maxVel;
	

	public Player(String name) {
		super(name);
	}

	public Player(String name, Vector2D position, int width, int height, float rotation, Map map) {
		super(name, position, width, height, rotation);

		maxVel = 100;
		direction = Direction.DOWN;
		color = Color.GREEN;
		offset.x = -width / 2;
		offset.y = -height;
		initializeComponents(map);
	}

	private void initializeComponents(Map map) {
		addComponent(new TileMapCollisionBehavior(map, 1));
	}
	
	@Override
	public void render(Graphics2D dbg) {
		// TODO Auto-generated method stub
		super.render(dbg);
	 
		
	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);
 
		setDirection();
		
		position.add(new Vector2D(speed.x * diffTime / 1000f, speed.y * diffTime / 1000f)) ;
	}
	
	public void setDirection() {
		switch (direction) {
		case DOWN:
			speed.x = 0;
			speed.y = maxVel;
		case UP:
			speed.x = 0;
			speed.y = -maxVel;
			break;
		case LEFT:
			speed.x = -maxVel;
			speed.y = 0;
			break;
		case RIGHT:
			speed.x = maxVel;
			speed.y = 0;
			break;
		case DOWN_LEFT:
			speed.x = -maxVel;
			speed.y = maxVel;
			break;
		case DOWN_RIGHT:
			speed.x = maxVel;
			speed.y = maxVel;
			break;
		case STOP:
			speed.x = speed.y = 0;
			break;
		case UP_RIGHT:
			speed.x = maxVel;
			speed.y = -maxVel;
			break;
		case UP_LEFT:
			speed.x = -maxVel;
			speed.y = -maxVel;
			break;
		default:
			break;
		}
	}
}
