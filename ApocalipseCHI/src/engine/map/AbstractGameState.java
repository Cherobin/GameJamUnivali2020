 
package engine.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import engine.core.game.GameStateManager;
import engine.entities.Component;
import engine.entities.GameEntity;
import engine.entities.GameObject;
import engine.ia.states.FSM;
import engine.ia.states.GameState;

 
public abstract class AbstractGameState implements GameState {

 
	private static GameStateManager sm;

	public String name = "default";
 
	public Map<String, GameObject> renderingStack = new ConcurrentHashMap<>();
	public List<GameObject> sortedEntities = new ArrayList<>();

	 
	@Override
	public String getName() {
		return name;
	}

 
	@Override
	public Map<String, GameObject> getEntities() {
		return renderingStack;
	}
 
	@Override
	public void initialize() {
		for (GameObject entity : renderingStack.values()) {
			entity.initialize();
		}
	}
 
	@Override
	public void setStateManager(GameStateManager stateManager) {
		sm = stateManager;

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

 
	@Override
	public void update(float diffTime) {

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
	public void render(Graphics2D g) {
 
		// draw entities.
		for (GameObject entity : sortedEntities) {
			if (entity.getComponents() != null && entity.getComponents().size() > 0) {
				for (Component c : entity.getComponents().values()) {
					c.render(entity, g);
				}
			}
			entity.render(g);
		}
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
	public boolean evaluate(FSM fsm) {
		return false;
	}

 
	public static GameStateManager getSm() {
		return sm;
	}

	public static void setSm(GameStateManager sm) {
		AbstractGameState.sm = sm;
	}
 
	public List<GameObject> getSortedEntities() {
		return sortedEntities;
	}
 
	public void setSortedEntities(List<GameObject> sortedEntities) {
		this.sortedEntities = sortedEntities;
	}
 
	public void setName(String name) {
		this.name = name;
	}

}
