package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.mapeditor.core.Map;

import com.sun.javafx.geom.Vec2d;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMap;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Direction;
import engine.utils.Vector2D;
import old.Constantes;

public class Player extends GameEntity implements GameObject {

	private int maxVel;

	BufferedImage myimage;
	public Vector2D mousePosition;

	TileMap tilemap;

	public Player(String name) {
		super(name);
	}

	public Player(String name, Vector2D position, int width, int height, float rotation, TileMap tilemap) {
		super(name, position, width, height, rotation);

		maxVel = 100; 
		color = Color.GREEN;
		offset.x = 0;
		offset.y = 0;
		oldPosition = new Vector2D();
		initializeComponents(tilemap);
		this.tilemap = tilemap;
		myimage = Constantes.personagem1;
		mousePosition = new Vector2D();
	}

	private void initializeComponents(TileMap tilemap) {
		this.tilemap = tilemap;
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
	}

	@Override
	public void render(Graphics2D dbg) { 
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation);
		dbg.drawImage(myimage, -myimage.getWidth() / 2, -myimage.getHeight() / 2, null);

		dbg.setTransform(t);
	}

	@Override
	public void update(float diffTime) {
		super.update(diffTime);

		oldPosition.set(position);

		setDirection();

		position.x += speed.x * diffTime / 1000f;
		position.y += speed.y * diffTime / 1000f;

		rotation = (float) Math.atan2((mousePosition.y + tilemap.getTelaY()) - position.y,
				(mousePosition.x + tilemap.getTelaX()) - position.x);

		// System.out.println(""+position.x+" "+position.y+" "+direction+" "+speed.x+"
		// "+speed.y+" "+maxVel);

		tilemap.posicionaTela((int) (position.x - Constantes.telaW / 2), (int) (position.y - Constantes.telaH / 2));

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
