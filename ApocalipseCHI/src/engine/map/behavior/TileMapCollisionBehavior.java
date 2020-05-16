package engine.map.behavior;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.utils.Direction;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
 
public class TileMapCollisionBehavior extends AbstractComponent implements Component {
 
	protected Map map;
 
	protected int layerIndex;

	protected TileLayer tl = null;
 
	public TileMapCollisionBehavior(Map map, int layerIndex) {
		this.layerIndex = layerIndex;
		this.map = map;
		initialize(null);
	}

	@Override
	public void initialize(GameObject e) {
		layerIndex = findLayerOnName("foreground"); // << collision layer
		tl = (TileLayer) map.getLayer(layerIndex);
	}


	private int findLayerOnName(String name) {
		int idx = 0;
		for (MapLayer layer : map.getLayers()) {
			if (layer.getName().equals(name)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}

	@Override
	public void update(GameObject e, float diffTime) {

		if (e != null) {
			GameEntity ge = (GameEntity) e;  
			//System.out.println(	ge.toString()); 
			if (map != null) {
				// read sensors
				Tile tileUL = tl.getTileAt((int) ((ge.position.x + ge.offset.x) / map.getTileWidth()),
						(int) ((ge.position.y + ge.offset.y + ge.height) / map.getTileHeight()));
				Tile tileUR = tl.getTileAt((int) ((ge.position.x + ge.offset.x + ge.width) / map.getTileWidth()),
						(int) ((ge.position.y + ge.offset.y + ge.height) / map.getTileHeight()) + 1);
				Tile tileBL = tl.getTileAt((int) ((ge.position.x + ge.offset.x) / map.getTileWidth() + 1),
						(int) ((ge.position.y + ge.offset.y) / map.getTileHeight()) + 2);
				Tile tileBR = tl.getTileAt((int) ((ge.position.x + ge.offset.x + ge.width) / map.getTileWidth() + 1),
						(int) ((ge.position.y + ge.offset.y) / map.getTileHeight()) + 2);

				//TODO tratar colisão
				if (tileUL != null || tileUR != null || tileBL != null || tileBR != null ) {
					System.out.println("colide");
					 ge.position = ge.oldPosition; 
					 ge.speed.x =  ge.speed.y = 0;
				}
				 
			}

		}
	}
}
