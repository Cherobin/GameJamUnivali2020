package engine.map;

import java.awt.Graphics2D;
import java.util.HashMap;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.view.OrthogonalRenderer;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;


public class TileMap extends GameEntity implements GameObject {

	private OrthogonalRenderer renderer;
	private Map map;

	public TileMap(String name) {
		super(name);
		layer = -1;
		priority = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void update(float diffTime) {

	}

	@Override
	public void render(Graphics2D g2d) {
		
		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof TileLayer) {
				if(!layer.getName().equals("roof")) {
					renderer.paintTileLayer(g2d, (TileLayer) layer);
				}
			}
			if(layer instanceof ObjectGroup){
				switch(layer.getName()){
				case "collision":
					// TODO
					break;
				case "objects":
					// TODO
					break;
				case "fx":
					// TODO
					break;
				}
			}
		}
	}
	
	public void renderRoof(Graphics2D g2d) {
		
		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof TileLayer) {
				if(layer.getName().equals("roof")) {
					renderer.paintTileLayer(g2d, (TileLayer) layer);
				}
			}
			if(layer instanceof ObjectGroup){
				switch(layer.getName()){
				case "collision":
					// TODO
					break;
				case "objects":
					// TODO
					break;
				case "fx":
					// TODO
					break;
				}
			}
		}
	}

	@Override
	public java.util.Map<String, Component> getComponents() {
		return components;
	}

	@Override
	public int compareTo(GameObject o) {
		return 0;
	}
 
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
		components = new HashMap<>();
		renderer = new OrthogonalRenderer(map);
	}
	
	public boolean isUnderRoof(int x,int y) {
		TileLayer tl = (TileLayer)map.getLayer(2); // RoofLayer
		
		Tile tile = tl.getTileAt((int) (x/ map.getTileWidth()),(int) (y / map.getTileHeight()));
		
		if(tile!=null) {
			return true;
		}
		
		return false;
	}

}
