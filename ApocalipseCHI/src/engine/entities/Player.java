package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.core.game.CanvasGame;
import engine.map.TileMap;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Vector2D;
import old.Constantes;

public class Player extends GameEntity implements GameObject {

	private int maxVel;

	BufferedImage myimage;
	public Vector2D mousePosition;

	TileMap tilemap;

	float timeToFire;
	float timerFire;

	public Player(String name) {
		super(name);
	}

	public Player(String name, Vector2D position, int width, int height, float rotation) {
		super(name, position, width, height, rotation);
		maxVel = 100;
		color = Color.GREEN;
		offset.x = 0;
		offset.y = 0;
		oldPosition = new Vector2D();
		myimage = Constantes.personagem1;
		mousePosition = new Vector2D();
		timeToFire = 0.2f;
		timerFire= 0;
		maxLife = life = 1000; 
	}

	public void initializeComponents(TileMap tilemap) {
		this.tilemap = tilemap;
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
	}

	@Override
	public void render(Graphics2D dbg) {
		super.render(dbg);

		dbg.setColor(Color.white);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 22, 40, 7);
		
		dbg.setColor(Color.red);
		dbg.fillRect((int)position.x - tilemap.getTelaX() - 20, (int) position.y - tilemap.getTelaY() - 20, (life*40)/maxLife, 5);
		
		
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation);
		dbg.drawImage(myimage, -myimage.getWidth() / 2, -myimage.getHeight() / 2, null);

		dbg.setColor(Color.black);
	
		
		dbg.setTransform(t);
	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);

		if(life < 0) {
			alive = false;
		}
	
		if(inCollider) {
			position.set(oldPosition);
    	}
		
		oldPosition.set(position);

		setDirection(); 

		rotation = (float) Math.atan2((mousePosition.y + tilemap.getTelaY()) - position.y,
				(mousePosition.x + tilemap.getTelaX()) - position.x);

		// System.out.println(""+position.x+" "+position.y+" "+direction+" "+speed.x+"
		// "+speed.y+" "+maxVel);

		tilemap.posicionaTela((int) (position.x - Constantes.telaW / 2), (int) (position.y - Constantes.telaH / 2));

		if(FIRE) {
			timerFire+= diffTime/1000; 
			if(timerFire > timeToFire) {
				timerFire=0;
				fire();
			}
		} 
		
		position.x += speed.x * diffTime / 1000f;
		position.y += speed.y * diffTime / 1000f;
		 
	}

	public void fire() {
		Particle p = new Particle("fire", 20, new Vector2D(position.x, position.y), new Vector2D(200, 200), new Vector2D(-width, 0), rotation, 4,
				Color.black, 1000, tilemap, this); 
		 CanvasGame.sortedEntities.add(p);

	}

	public void setDirection() {
		if (RIGHT) {
			speed.x = maxVel;
		} else if (LEFT) {
			speed.x = -maxVel;
		} else {
			speed.x = 0;
		}

		if (DOWN) {
			speed.y = maxVel;
		} else if (UP) {
			speed.y = -maxVel;
		} else {
			speed.y = 0;
		}
	}

}
