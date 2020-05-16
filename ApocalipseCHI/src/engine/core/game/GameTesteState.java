/**
 * SnapGames
 * @year 2016
 */
package engine.core.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.entities.Player;
import engine.ia.states.GameState;
import engine.map.TileMap;
import engine.map.TileMapLoader;
import tiled.core.Map;

 
public class GameTesteState implements GameState {

	private GameEntityBuilder seb;
	private TileMapLoader tml; 
	
	private TileMap tileMap = null;
	private Player player = null;
	 
	 
		public java.util.Map<String, GameObject> renderingStack = new ConcurrentHashMap<>();
		public List<GameObject> sortedEntities = new ArrayList<>();
	
	public GameTesteState() {
		initialize(); 
	}
  
	@Override
	public void initialize() {
		tileMap = new TileMap("tilemap"); 
		seb = new GameEntityBuilder();
		tml = new TileMapLoader(seb);
		Map map = null;
		map = tml.load(this, "res/maps/level-1-1.tmx");
		// Attache target to enemy for EnemyBehavior component's sensors
		player = (Player) renderingStack.get("player");
		
		/*for (GameObject go : renderingStack.values()) {
			if (go instanceof Enemy) {
				Enemy e = (Enemy) go;
				e.setTarget(player, 0.3f, 64, 256);
			}
		}*/
	 
		if (map != null) {
			tileMap.setMap(map);
			tileMap.initialize();
			
			for (GameObject entity : renderingStack.values()) {
				entity.initialize();
			}
			
		}
		
	
	}


	@Override
	public String getName() { 
		return this.getName();
	}

	@Override
	public void update(float diffTime) {   
		
		if(tileMap!=null)
			tileMap.update(diffTime);
		
		// update entities.
		for (GameObject entity : sortedEntities) {
			entity.update(diffTime);
			if (entity.getComponents() != null && entity.getComponents().size() > 0) {
				for (Component c : entity.getComponents().values()) {
					c.update(entity, diffTime);
				}
			}
		} 
	}

	@Override
	public void render(Graphics2D dbg) {   
		if(tileMap!=null)
			tileMap.render(dbg);
		
		// draw entities.
		for (GameObject entity : sortedEntities) {
			if (entity.getComponents() != null && entity.getComponents().size() > 0) {
				for (Component c : entity.getComponents().values()) {
					c.render(entity, dbg);
				}
			}
			entity.render(dbg);
		}
		
	} 
	
	public List<GameObject> getSortedEntities() {
		return sortedEntities;
	}
 
	public void setSortedEntities(List<GameObject> sortedEntities) {
		this.sortedEntities = sortedEntities;
	}
	
	private void sortEntities() {
		
		Collection<GameObject> ents = renderingStack.values();
		sortedEntities.clear();
		sortedEntities.addAll(ents);
		Collections.sort(sortedEntities, new Comparator<GameObject>() {
			@Override
			public int compare(GameObject o1, GameObject o2) {
				GameEntity e1 = (GameEntity) o1;
				GameEntity e2 = (GameEntity) o2;
				return (e1.layer > e2.layer ? 1 : -1);
			}
		});
	}
	
	public void addEntity(GameObject entity) {
		renderingStack.put(entity.getName(), entity);
		sortEntities();
	}

	public void removeEntity(GameObject entity) {
		if (renderingStack.containsKey(entity.getName())) {
			renderingStack.remove(entity);
		}
		sortEntities();
	}
	
	private void drawSystemData(Graphics2D g) {
		Runtime runtime = Runtime.getRuntime();

		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		sb.append("free:" + String.format("%06d", freeMemory / 1024) + "/");
		sb.append("alloc:" + String.format("%06d", allocatedMemory / 1024) + "/");
		sb.append("max:" + String.format("%06d", maxMemory / 1024) + "/");
		sb.append("total:" + String.format("%06d", (freeMemory + (maxMemory - allocatedMemory)) / 1024));
		String systemData = sb.toString();

		String objects = String.format("nb-GE:%d", sortedEntities.size());

	}

	@Override
	public java.util.Map<String, GameObject> getEntities() { 
		return renderingStack;
	}
 
}
