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

	BufferedImage myimage;
	TileMap tilemap;
	float timeToFire;
	float timerFire;
	
	
	public Enemy(String name) {
		super(name);
	}

	public Enemy(String name, Vector2D position, int width, int height, float rotation) {
		super(name, position, width, height, rotation);
		color = Color.RED;
		offset.x = -width / 2;
		offset.y = -height;
		oldPosition = new Vector2D();
		myimage = Constantes.personagem1;
		speed = new Vector2D(Constantes.rnd.nextInt(50)+50,Constantes.rnd.nextInt(50)+50);
		this.tilemap = CanvasGame.tileMap;
		initializeComponents(tilemap);
		timeToFire = 0.5f;
		timerFire= 0;
		alive = true;
		oldPosition = position;
		maxLife = life = 100;
 
	}

	@Override
	public void render(Graphics2D dbg) {
		super.render(dbg);
		
		dbg.setColor(Color.white);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 22, 40, 7);
		
		dbg.setColor(Color.red);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 20, (life*40)/maxLife, 5);
		
		
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y  - tilemap.getTelaY());
		dbg.rotate(rotation);
		
		
		dbg.drawImage(myimage, -myimage.getWidth() / 2, -myimage.getHeight() / 2, null);
		dbg.setTransform(t);

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		 
		if(FIRE) {
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
				Color.black, 1000, tilemap);
	 
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
