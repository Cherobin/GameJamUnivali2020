package engine.map.behavior;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.map.TileMap;
import engine.utils.Direction;
 
public class TileMapCollisionBehavior extends AbstractComponent implements Component {
 
	protected TileMap map;
 
	protected int layerIndex;
 
	public TileMapCollisionBehavior(TileMap map, int layerIndex) {
		this.layerIndex = layerIndex;
		this.map = map;
		initialize(null);
	}

	@Override
	public void initialize(GameObject e) {
		layerIndex = map.wallID; // << collision layer
	}


	@Override
	public void update(GameObject e, float diffTime) {
		if (e != null) {
			GameEntity ge = (GameEntity) e;  
			//System.out.println(	ge.toString()); 
			//if (map.map != null) {
				
				//Tile tileCenter = tl.getTileAt((int) ((ge.position.x + ge.offset.x) / map.map.getTileWidth()),
				//		(int) ((ge.position.y + ge.offset.y) / map.map.getTileHeight()));
				
				// read sensors
				//Tile tileUL = tl.getTileAt((int) ((ge.position.x + ge.offset.x) / map.getTileWidth()),
				//		(int) ((ge.position.y + ge.offset.y + ge.height) / map.getTileHeight()));
				
				//Tile tileUR = tl.getTileAt((int) ((ge.position.x + ge.offset.x + ge.width) / map.getTileWidth()),
				//		(int) ((ge.position.y + ge.offset.y + ge.height) / map.getTileHeight()) + 1);
				//Tile tileBL = tl.getTileAt((int) ((ge.position.x + ge.offset.x) / map.getTileWidth() + 1),
				//		(int) ((ge.position.y + ge.offset.y) / map.getTileHeight()) + 2);
				//Tile tileBR = tl.getTileAt((int) ((ge.position.x + ge.offset.x + ge.width) / map.getTileWidth() + 1),
				//		(int) ((ge.position.y + ge.offset.y) / map.getTileHeight()) + 2);

				//TODO tratar colisão
				///if (tileUL != null || tileUR != null || tileBL != null || tileBR != null ) {
				
				boolean colisionCenter = map.colision((int)ge.position.x,(int)ge.position.y);
				//System.out.println("colisionCenter "+colisionCenter);
				
				if(colisionCenter) {
					//System.out.println("colide "+colisionCenter);
					//TODO CEROBIN AMADOR
					// ge.position = ge.oldPosition; 
					
					ge.position.set(ge.oldPosition);
					
					 ge.speed.x =  ge.speed.y = 0;
				}
				 
			//}

		}
	}
}
