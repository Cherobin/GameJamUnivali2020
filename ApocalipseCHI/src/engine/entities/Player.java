package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.javafx.geom.Vec2d;

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
		direction = Direction.STOP;
		color = Color.GREEN;
		offset.x = -width / 2;
		offset.y = -height;
		oldPosition = new Vector2D();
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
		 
		oldPosition = position; 
		 
		setDirection();
		
		position.x += speed.x * diffTime / 1000f;
		position.y += speed.y * diffTime / 1000f; 
		
	}
	
 public void setDirection() {
		switch (direction) {
		case DOWN:
			speed.x = 0;
			speed.y = maxVel;
			break;
		case UP:
			speed.x = 0;
			speed.y = maxVel * -1;
			break;
		case LEFT:
			speed.x = maxVel * -1;
			speed.y = 0;
			break;
		case RIGHT:
			speed.x = maxVel;
			speed.y = 0;
			break;
		case DOWN_LEFT:
			speed.x = maxVel * -1;
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
			speed.y = maxVel * -1;
			break;
		case UP_LEFT:
			speed.x = maxVel * -1;
			speed.y = maxVel * -1;
			break;
		default:
			break;
		}
	}
 
}
