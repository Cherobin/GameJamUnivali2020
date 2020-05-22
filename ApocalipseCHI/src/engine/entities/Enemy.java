package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.core.game.CanvasGame;
import engine.map.TileMap;
import engine.map.behavior.EnemyBehavior;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Vector2D;
import old.Constantes;

public class Enemy extends GameEntity implements GameObject {

	BufferedImage charset;
	TileMap tilemap;
	float timeToFire;
	float timerFire;
	
	boolean primaryWeapon;
	boolean secondaryWeapon;
	boolean meleeWeapon;
	boolean meleeAtack;
	
	int charsetX, charsetY;
	
	 float frameTime = 100;
	 float timer = 0;
     int animation = 0;
     int frame = 0;
     
     
	public Enemy(String name) {
		super(name);
	}

	public Enemy(String name, Vector2D position, int width, int height, float rotation) {
		super(name, position, width, height, rotation);
		color = Color.RED;
		offset.x = -width / 2;
		offset.y = -height;
		charsetX = width;
		charsetY = height;
		oldPosition = new Vector2D();
		charset = Constantes.enemy1;
		speed = new Vector2D(Constantes.rnd.nextInt(50)+50,Constantes.rnd.nextInt(50)+50);
		this.tilemap = CanvasGame.tileMap;
		initializeComponents(tilemap);
		timeToFire = 0.5f;
		timerFire= 0;
		alive = true;
		oldPosition = position;
		maxLife = life = 100; 
		secondaryWeapon = true;;
		primaryWeapon = false;
		meleeWeapon = false;
		meleeAtack = false;
 
	}

	@Override
	public void render(Graphics2D dbg) {
		super.render(dbg);
	
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y  - tilemap.getTelaY());
		dbg.rotate(rotation);
		
		dbg.drawImage(charset, -charsetX/2, -charsetY/2, charsetX/2, charsetY/2,
				(frame*charsetX),
				(animation*charsetY),
				charsetX+(frame*charsetX),
				charsetY+(animation*charsetY),null);
		      
	 	dbg.setTransform(t);
		
		
		dbg.setColor(Color.white);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 22, 40, 7);
		
		dbg.setColor(Color.red);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 20, (life*40)/maxLife, 5);
		
		

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		 
		timer+=dt;
 
		
		//TODO FIX ME
		if (primaryWeapon) {
			animation = 0;
		} else if (secondaryWeapon) {
			animation = 1;
		} else if (meleeWeapon) {
			animation = 2;
		} else if (meleeAtack) {
			animation = 3;
		}
	 
		
		if(speed.x  != 0 || speed.y != 0) {
	     frame = ((int)(timer/frameTime))%3;
		}
	     
		if(FIRE && (primaryWeapon || secondaryWeapon)) {
			timerFire+= dt/1000; 
			if(timerFire > timeToFire) {
				timerFire=0;
				fire();
			}
		}
		
		if(life < 0) {
			alive = false;
		}
	}

	public void fire() {
		Particle p = new Particle("fire", 5, new Vector2D(position.x, position.y),new Vector2D(200, 200), new Vector2D(-width, 0), rotation, 4,
				Color.black, 1000, tilemap, this);
	 
		CanvasGame.sortedEntities.add(p);
		 

	}
	private void initializeComponents(TileMap tilemap) {
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
		//addComponent(new MoveElementBehavior(speed));
	}

	public void setTarget(GameEntity target, float speed, float sensorDistance, float viewDistance, TileMap tilemap) {
		  addComponent(new EnemyBehavior(target, speed,
				  sensorDistance, viewDistance, tilemap));
	}

}
