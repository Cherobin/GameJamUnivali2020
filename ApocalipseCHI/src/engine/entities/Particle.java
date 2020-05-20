package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import engine.base.entities.GameEntity;
import engine.map.TileMap;
import engine.map.behavior.MoveElementBehavior;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Vector2D;
import old.Constantes;

public class Particle extends GameEntity {
 
	public float radius; 
	public Color color; 
	public float expireTime; 
	public float decay; 
	TileMap tilemap;
	
	public Particle(String name, Vector2D pos, Vector2D velocity, float rotation, float radius, Color color, float expireTime, TileMap tilemap) {
		super(name); //create a unic id
		this.position = pos;
		this.speed = velocity;
		this.radius = radius;
		this.width=(int)(radius);
		this.color = color;
		this.expireTime = expireTime; 
		this.tilemap = tilemap;
		this.rotation = rotation;
		offset = new Vector2D(2, 2);
		initializeComponents(tilemap);
	}
 
	@Override
	public boolean isAlive() { 
		return expireTime > 0 && alive ;
	}

	@Override
	public void render(Graphics2D dbg) {
		//super.render(dbg);
		
		AffineTransform t = dbg.getTransform();
		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation);
		dbg.fillOval((int) (+offset.x/2), (int) (+offset.y/2), (int) this.radius, (int) this.radius);
		dbg.setTransform(t); 
	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);
		 
		expireTime -= diffTime/1000f;
	}
	
	private void initializeComponents(TileMap tilemap) {
		this.tilemap = tilemap; 
		addComponent(new MoveElementBehavior(speed));
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
	}
	
}
