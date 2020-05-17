package engine.core.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.core.MyCanvas;
import engine.entities.Player;
import engine.ia.states.GameState;
import engine.map.TileMap;
import engine.map.TileMapLoader;
import engine.utils.Direction;

public class CanvasGame extends MyCanvas implements GameState {

	private GameEntityBuilder seb;
	private TileMapLoader tml;

	private TileMap tileMap = null;
	private Player player = null;
	private org.mapeditor.core.Map map = null;

	public java.util.Map<String, GameObject> renderingStack;
	public List<GameObject> sortedEntities;

	public CanvasGame() {
		sortedEntities = new ArrayList<>();
		renderingStack = new ConcurrentHashMap<>();
		tileMap = new TileMap("tilemap");
		
		seb = new GameEntityBuilder();
		tml = new TileMapLoader(seb);
	  
		map = tml.load(this, "res//maps//level-2-1.tmx"); 
 
		// Attache target to enemy for EnemyBehavior component's sensors
		player = (Player) renderingStack.get("player");

	
		tileMap.setMap(map); 

		//addEntity(player);
	 
		  
		// Attache target to enemy for EnemyBehavior component's sensors

		/*
		 * for (GameObject go : renderingStack.values()) { if (go instanceof Enemy) {
		 * Enemy e = (Enemy) go; e.setTarget(player, 0.3f, 64, 256); } } addEntity(e)
		 */

	}

	@Override
	public void update(float diffTime) {

		// update map
		if (tileMap != null)
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

		// draw map
		if (tileMap != null)
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

		//systemdata
		drawSystemData(dbg);
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.direction = Direction.LEFT;
		}

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.direction = Direction.RIGHT;
		}

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.direction = Direction.UP;
		}

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.direction = Direction.DOWN;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		player.direction = Direction.STOP;
	 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() { 
		return this.getName();
	}


	@Override
	public Map<String, GameObject> getEntities() { 
		return renderingStack;
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
		g.drawString(systemData, 10, 30);
	}
}
