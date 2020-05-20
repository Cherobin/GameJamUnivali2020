package engine.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.base.entities.GameEntity;
import engine.map.TileMap;
import engine.map.behavior.MoveElementBehavior;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Vector2D;

public class Particle extends GameEntity {

 
	public float radius; 
	public Color color; 
	public float expireTime; 
	public float decay; 
	TileMap tilemap;
	
	public Particle(Vector2D pos, Vector2D velocity, float radius, Color color, float expireTime, TileMap tilemap) {
		super("particle");
		this.position = pos;
		this.speed = velocity;
		this.radius = radius;
		this.width=(int)(radius);
		this.color = color;
		this.expireTime = expireTime; 
		this.tilemap = tilemap;
		initializeComponents(tilemap);
	}
 
	public boolean isAlive() {
		return expireTime > 0;
	}

	@Override
	public void render(Graphics2D dbg) {
		//super.render(dbg);
		
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation);
		
		dbg.setComposite(AlphaComposite.SrcIn.derive(0.5f));
		GradientPaint paint = new GradientPaint(23,23, Color.RED,25, 24, Color.BLUE);
		dbg.setPaint(paint);
		dbg.fillOval((int) (+offset.x), (int) (+offset.y), (int) this.radius, (int) this.radius);
		dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		
		dbg.setTransform(t); 
	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);
		 
		 
		expireTime -= diffTime/1000f;
		System.out.println(expireTime);
	}
	
	private void initializeComponents(TileMap tilemap) {
		this.tilemap = tilemap; 
		addComponent(new MoveElementBehavior(speed));
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
	}
	
}
