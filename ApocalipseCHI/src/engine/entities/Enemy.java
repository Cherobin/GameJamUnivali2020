package engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMap;
import engine.map.behavior.EnemyBehavior;
import engine.map.behavior.MoveElementBehavior;
import engine.map.behavior.TileMapCollisionBehavior;
import engine.utils.Vector2D;
import old.Constantes;

public class Enemy extends GameEntity implements GameObject {

	BufferedImage myimage;
	TileMap tilemap;

	public Enemy(String name) {
		super(name);
	}

	public Enemy(String name, Vector2D position, int width, int height, float rotation, TileMap tilemap) {
		super(name, position, width, height, rotation);

		color = Color.RED;
		offset.x = -width / 2;
		offset.y = -height;
		oldPosition = new Vector2D();
		myimage = Constantes.personagem1;
		speed = new Vector2D(100, 100);
		initializeComponents(tilemap);
	}

	@Override
	public void render(Graphics2D dbg) {
		super.render(dbg);
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - tilemap.getTelaX(), position.y - tilemap.getTelaY());
		dbg.rotate(rotation);
		dbg.drawImage(myimage, -myimage.getWidth() / 2, -myimage.getHeight() / 2, null);
		dbg.setTransform(t);

	}

	@Override
	public void update(float dt) {
		super.update(dt);
	
	}

	private void initializeComponents(TileMap tilemap) {
		this.tilemap = tilemap;
		addComponent(new MoveElementBehavior(speed));
		addComponent(new TileMapCollisionBehavior(tilemap, 1));
	}

	public void setTarget(GameEntity target, float speed, float sensorDistance, float viewDistance) {
		  addComponent(new EnemyBehavior(target, (float) Math.random() * speed,
				  sensorDistance, viewDistance));
	}

}
