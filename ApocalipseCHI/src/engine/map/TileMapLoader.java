package engine.map;
 
import java.io.File;
import java.io.FileInputStream;

import org.mapeditor.core.MapLayer;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.io.TMXMapReader;

import engine.base.entities.GameObject;
import engine.base.entities.GameState;
import engine.core.game.CanvasGame;
import engine.core.game.GameEntityBuilder;


 
public class TileMapLoader { 
 
	private static org.mapeditor.core.Map map = null;

	//private EntityBuilder builder;
	private GameEntityBuilder builder;
	

	public TileMapLoader(GameEntityBuilder builder) {
		this.builder = builder;
	}
	
 
	public org.mapeditor.core.Map load(GameState gs, String path) {
		TMXMapReader tmr = new TMXMapReader();
		 
		try {
			String basepath = new File(".").getCanonicalPath();
			FileInputStream fin = new FileInputStream(basepath+"//"+path);
			System.out.println(""+fin.available());
			map = tmr.readMap(basepath+"//"+path);
	 
			for (MapLayer ml : map.getLayers()) {
				if (ml instanceof ObjectGroup) {
					switch (ml.getName()) {
					case "actors":
						buildEntities(gs, ml);
						break;
					}

				}
			}

		} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
		}
		return map;
	}

	private void buildEntities(GameState gs, MapLayer ml) { 
		((ObjectGroup) ml).getObjects().forEach(o -> { 
			GameObject go = this.builder.build(gs, map , o);
			if (go != null) { 
				CanvasGame.addEntity(go); 
			} 		 
		});
	}
}
