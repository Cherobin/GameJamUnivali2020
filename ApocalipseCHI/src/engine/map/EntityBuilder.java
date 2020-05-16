package engine.map;

import engine.base.entities.GameObject;
import engine.ia.states.GameState;
import tiled.core.Map;
import tiled.core.MapObject;

public interface EntityBuilder {

	public GameObject build(GameState gs, Map map, MapObject o);
}
