package engine.core.game;
 

import org.mapeditor.core.Map;
import org.mapeditor.core.MapObject;

import engine.base.entities.GameObject;
import engine.entities.Enemy;
import engine.entities.GameState;
import engine.entities.Player;
import engine.map.EntityBuilder;
import engine.map.TileMap;
import engine.utils.Vector2D;

 
public class GameEntityBuilder implements EntityBuilder {

 
	@Override
	public GameObject build(GameState gs, Map map, MapObject o) {
		GameObject go = null;
		
		
		switch (o.getType()) {
		case "Enemy": 
			Vector2D position = new Vector2D((float) o.getX(), (float) o.getY());
		 	Enemy e = new Enemy(o.getName(), position, o.getWidth().intValue(), o.getHeight().intValue(), 0.0f);
			e.setTarget(CanvasGame.player, 80, 300, 150, CanvasGame.tileMap);
		 	go = e; 
			break;
		case "Player":
			position = new Vector2D((float) o.getX(), (float) o.getY()); 
			Player p = new Player(o.getName(), position, o.getWidth().intValue(), o.getHeight().intValue(), 0.0f);
			go = p; 
			break;
		}
		return go;
	}

}
