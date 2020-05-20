package engine.map.behavior;

import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMap;

public class TileMapCollisionBehavior extends AbstractComponent implements Component {

	protected TileMap map;

	protected int layerIndex;
<<<<<<< HEAD

	protected TileLayer tl = null;

=======
 
>>>>>>> 43dff3048134e853a240709ee44ddb6093983406
	public TileMapCollisionBehavior(TileMap map, int layerIndex) {
		this.layerIndex = layerIndex;
		this.map = map;
		initialize(null);
	}

	@Override
	public void initialize(GameObject e) {
<<<<<<< HEAD
		layerIndex = findLayerOnName("wall"); // << collision layer
		tl = (TileLayer) map.map.getLayer(layerIndex); 
	}

	private int findLayerOnName(String name) {
		int idx = 0;
		for (MapLayer layer : map.map.getLayers()) {
			if (layer.getName().equals(name)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}
=======
		layerIndex = map.wallID; // << collision layer
	}

>>>>>>> 43dff3048134e853a240709ee44ddb6093983406

	@Override
	public void update(GameObject e, float diffTime) {
		if (e != null) {
			GameEntity ge = (GameEntity) e;
			boolean colisionCenter = map.colision((int) ge.position.x, (int) ge.position.y);

			if (colisionCenter) {
				ge.position.set(ge.oldPosition);
				ge.speed.x = ge.speed.y = 0;
			}

		}
	}
}
