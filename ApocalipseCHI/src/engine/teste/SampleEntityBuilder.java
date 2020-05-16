/**
 * SnapGames
 * 
 * @year 2016
 * 
 */
package engine.teste;

import engine.entities.GameObject;
import engine.ia.states.GameState;
import engine.map.EntityBuilder;
import engine.utils.Vector2D;
import tiled.core.Map;
import tiled.core.MapObject;

 
public class SampleEntityBuilder implements EntityBuilder {

 
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
			//TODO criar Player
			//go = e;
			break;
		}
		return go;
	}

}
