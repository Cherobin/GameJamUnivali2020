package engine.map;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapObject;

import engine.base.entities.GameObject;
import engine.ia.states.GameState;

public interface EntityBuilder {

	public GameObject build(GameState gs, TileMap tilemap, Map map, MapObject o);
}
