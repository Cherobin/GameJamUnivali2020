package engine.map;

import engine.entities.AbstractComponent;
import engine.entities.Component;
import engine.entities.GameEntity;
import engine.entities.GameObject;
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
	}

	@Override
	public void initialize(GameObject e) {
		layerIndex = findLayerOnName("foreground");
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

				// detect if blocking on vertical direction
				if (tileUL != null && tileUR != null && Math.signum(ge.speed.y) < 0) {
					ge.speed.y = 0.0f; 
				}
				if (tileBL != null && tileBR != null && Math.signum(ge.speed.y) > 0) {
					ge.speed.y = 0.0f; 
					ge.direction = Direction.STOP;
				}

				// detect if blocking on horizontal direction
				if ((tileUL != null && tileBL != null && Math.signum(ge.speed.y) < 0)) {
					ge.speed.x = 0.0f;
					ge.accel.x = 0.0f;
					ge.direction = Direction.STOP;
				}
				if (tileUR != null && tileBR != null && Math.signum(ge.speed.y) > 0) {
					ge.speed.x = 0.0f;
					ge.accel.x = 0.0f;
					ge.direction = Direction.STOP;
				}
			}

		}
	}
}
