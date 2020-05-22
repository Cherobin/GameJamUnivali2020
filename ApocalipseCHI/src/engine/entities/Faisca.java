package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.base.entities.GameEntity;
import engine.base.entities.GameEntity.GameEntityType;
import engine.core.game.CanvasGame;
import engine.map.TileMap; 
import engine.utils.Vector2D;

public class Faisca extends GameEntity {

	public float radius; 
	public Color color; 
	public float expireTime; 
	public float decay; 
	TileMap tilemap; 
	Vector2D velocity;
	float timer = 0;
	float alpha = 0;
	
	public Faisca(String name, Vector2D pos, Vector2D velocity, float rotation, float radius, float expireTime) {
		super(name);
		this.position = pos;
		this.speed = new Vector2D((float) (speed.x* Math.cos(rotation)), (float) (speed.y* Math.sin(rotation)));
		this.radius = radius;
		this.width=(int)(radius);
		this.color = Color.yellow;  
		this.rotation = rotation; 
		this.velocity = velocity;
		this.expireTime = expireTime; 
		alive = true;
		this.tilemap = CanvasGame.tileMap;
	
	}

	@Override
	public boolean isAlive() { 
		return alive ;
	}
	
	@Override
	public void render(Graphics2D dbg) {
   
		AffineTransform t = dbg.getTransform();
		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation); 
		alpha =  ((expireTime-timer)/(float)expireTime);
		dbg.setColor( new Color(192,192,192, (int)(255*(alpha < 0 ? 0 :alpha ))));
		dbg.fillOval((int)-radius/2, (int)-radius/2, (int)radius, (int)radius);
		dbg.setColor(Color.darkGray);
		dbg.fillOval((int)-radius/2+2, (int)-radius/2+2, (int)radius-2, (int)radius-2);

		dbg.setTransform(t);

	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);
 
		timer += diffTime/1000f; 

		if(timer > expireTime) {
			alive = false;
		}
	}

}

