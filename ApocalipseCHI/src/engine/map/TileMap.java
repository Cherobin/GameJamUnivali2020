package engine.map;

import java.awt.Graphics2D;
import java.util.HashMap;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.ObjectGroup;
import tiled.core.TileLayer;
import tiled.view.OrthogonalRenderer;


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
	public void update(float dt) {

	}

	@Override
	public void render(Graphics2D g2d) {
		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof TileLayer) {
				renderer.paintTileLayer(g2d, (TileLayer) layer);
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
	public void initialize() {
		renderer = new OrthogonalRenderer(map);
		components = new HashMap<>();
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
	}

}
