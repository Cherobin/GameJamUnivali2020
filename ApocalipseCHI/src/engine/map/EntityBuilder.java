package engine.map;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapObject;

import engine.base.entities.GameObject;
import engine.entities.GameState;

public interface EntityBuilder {

	public GameObject build(GameState gs, Map map, MapObject o); 
}
