package engine.map;
 
import java.net.URL;

import engine.base.entities.GameObject;
import engine.ia.states.GameState;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.ObjectGroup;
import tiled.io.TMXMapReader;

 
public class TileMapLoader { 
 
	private static Map map = null;

	private EntityBuilder builder;

	public TileMapLoader(EntityBuilder builder) {
		this.builder = builder;
	}
	
 
	public Map load(GameState gs, String path) {
		TMXMapReader tmr = new TMXMapReader();
		
		
		
		try {
			map = tmr.readMap(path);
			for (MapLayer ml : map.getLayers()) {
				if (ml instanceof ObjectGroup) {
					switch (ml.getName()) {
					case "actors":
						buildEntities(gs, ml);
						break;
					case "fx":
						buildEntities(gs, ml);
						break;
					case "collision":
						// TODO add collision bounding box initialization. 
						break;
					}

				}
			}

		} catch (Exception e) { 
					System.out.println(e.getLocalizedMessage());
		}
		return map;
	}

	private void buildEntities(GameState gs, MapLayer ml) {
		((ObjectGroup) ml).getObjects().forEachRemaining(o -> {
			GameObject go = this.builder.build(gs, map, o);
			if (go != null) {
				gs.getEntities().put(go.getName(), go);
			} else {
				System.out.println("buildEntities error ->"+o.getName());
			}
		});
	}
}
