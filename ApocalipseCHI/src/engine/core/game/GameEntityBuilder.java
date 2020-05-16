package engine.core.game;
 

import engine.base.entities.GameObject;
import engine.entities.Player;
import engine.ia.states.GameState;
import engine.map.EntityBuilder;
import engine.utils.Vector2D;
import tiled.core.Map;
import tiled.core.MapObject;

 
public class GameEntityBuilder implements EntityBuilder {

 
	@Override
	public GameObject build(GameState gs, Map map, MapObject o) {
		GameObject go = null;
		
		switch (o.getType()) {
		case "Enemy":
			Vector2D position = new Vector2D((float) o.getX(), (float) o.getY());
			//TODO criar enemy
			//go = e;
			break;
		case "Player":
			position = new Vector2D((float) o.getX(), (float) o.getY()); 
			Player p = new Player(o.getName(), position, (int) o.getWidth(), (int) o.getHeight(), 0.0f, map);
			go = p; 
			break;
		}
		return go;
	}

}
