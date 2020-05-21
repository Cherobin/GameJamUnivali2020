package engine.core.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import apiPS.OGG_Player;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.base.entities.GameState;
import engine.core.GamePanel;
import engine.core.MyCanvas;
import engine.entities.Enemy;
import engine.entities.Player;
import engine.map.TileMap;
import engine.map.TileMapLoader;
import engine.utils.Vector2D;
import old.Constantes;

public class CanvasGame extends MyCanvas implements GameState {

	private GameEntityBuilder seb;
	private TileMapLoader tml;

	public static TileMap tileMap = null;
	public static Player player = null;
	private org.mapeditor.core.Map map = null;

	private int tamanhoSensor = 350;
	private int tmanhoDaluz = 250;

	public static Map<String, GameObject> renderingStack;
	public List<GameObject> sortedEntities;

	BufferedImage roofImage;

	int[] roofData;

	public CanvasGame() {

		// tem que criar o player antes do tileMap por causa dos inimigos
		// depois de tudo adiciona ele na lista

		player = new Player("Dennis", new Vector2D(800, 1000), 32, 32, 0);

		roofImage = new BufferedImage(Constantes.telaW, Constantes.telaH, BufferedImage.TYPE_INT_ARGB);
		roofData = ((DataBufferInt) roofImage.getRaster().getDataBuffer()).getData();

		sortedEntities = new ArrayList<>();
		renderingStack = new ConcurrentHashMap<>();
		tileMap = new TileMap("tilemap");

		seb = new GameEntityBuilder();
		tml = new TileMapLoader(seb);

		map = tml.load(this, "res//maps//commercial01.tmx");
		tileMap.addMap(map);
		map = tml.load(this, "res//maps//commercial01.tmx");
		tileMap.addMap(map);
		map = tml.load(this, "res//maps//commercial01.tmx");
		tileMap.addMap(map);

		map = tml.load(this, "res//maps//residencial02.tmx"); 
		tileMap.addMap(map);
		map = tml.load(this, "res//maps//industrial02.tmx"); 
		tileMap.addMap(map);
		map = tml.load(this, "res//maps//commercial02.tmx"); 
		tileMap.addMap(map);
		
		tileMap.setMap(); 
		
		// adiciona o mapa para ele

		player.initializeComponents(tileMap);
		addEntity(player);

		/*
		 * Enemy enemy = new Enemy("Enemy",new Vector2D(24800, 24200),32,32,0,tileMap);
		 * enemy.setTarget(player, 80, 300, 150, tileMap );
		 * 
		 * addEntity(enemy);
		 * 
		 * Enemy enemy2 = new Enemy("Enemy2",new Vector2D(24800,
		 * 24210),32,32,0,tileMap); enemy2.setTarget(player, 60, 300, 150, tileMap );
		 * 
		 * addEntity(enemy2);
		 * 
		 * Enemy enemy3 = new Enemy("Enemy3",new Vector2D(24800,
		 * 24200),32,32,0,tileMap); enemy3.setTarget(player, 50, 300, 150, tileMap );
		 * 
		 * addEntity(enemy3);
		 * 
		 */

		// TESTE INICIAL DE PLAY OGG
		OGG_Player musicplayer = new OGG_Player();
		File ogg = new File("musica2_low.ogg");
		musicplayer.ExamplePlayer(ogg);
		// musicplayer.start();

		// Attache target to enemy for EnemyBehavior component's sensors

		for (GameObject go : renderingStack.values()) {
			if (go instanceof Enemy) {
				Enemy e = (Enemy) go;
				e.setTarget(player, 80, 300, 150, tileMap);
				addEntity(e);
			}
		}

	}

	@Override
	public void update(float diffTime) {

		// update map
		if (tileMap != null)
			tileMap.update(diffTime);

		for (int i = 0; i < sortedEntities.size(); i++) {
			GameObject entity = sortedEntities.get(i);
			if (!entity.isAlive()) {
				sortedEntities.remove(i);
				i--;
				continue;
			}
			entity.update(diffTime);
			if (entity.getComponents() != null && entity.getComponents().size() > 0) {
				for (Component c : entity.getComponents().values()) {
					// System.out.println(entity.getName() + " - " + c.getName());
					c.update(entity, diffTime);
				}
			}
		}
	}

	@Override
	public void render(Graphics2D dbg) {
		// TODO Retirar depois
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);

		// draw map
		if (tileMap != null)
			tileMap.render(dbg);
		

		for (int i = 0; i < sortedEntities.size(); i++) {
			GameObject entity = sortedEntities.get(i);
			if (entity.getComponents() != null && entity.getComponents().size() > 0) {
				for (Component c : entity.getComponents().values()) {
					c.render(entity, dbg);
				}
			}
			entity.render(dbg);
		}

		// draw map
		//System.out.println("underroof "+tileMap.isUnderRoof((int)player.position.x,(int)player.position.y));
		
		/*if (tileMap != null && !tileMap.isUnderRoof((int)player.position.x,(int)player.position.y)) {
			tileMap.renderRoof(dbg);
		} else {
			for (int i = 0; i < roofData.length; i++) {
				roofData[i] = 0;
			}
			Graphics2D graproof = (Graphics2D) roofImage.getGraphics();
			graproof.clip(new Rectangle(0, 0, Constantes.telaW, Constantes.telaH));
			// System.out.println(" "+graproof.getClip());
			tileMap.renderRoof(graproof);
			int cx = Constantes.telaW / 2;
			int cy = Constantes.telaH / 2;
			int distmax = 150 * 150;
			for (int i = 0; i < Constantes.telaH; i++) {
				for (int j = 0; j < Constantes.telaW; j++) {
					int dx = j - cx;
					int dy = i - cy;
					int dist = dx * dx + dy * dy;
					if (dist < distmax) {
						roofData[i * Constantes.telaW + j] = 0;
					}
				}
			}
			dbg.drawImage(roofImage, 0, 0, null);
		}*/
		
		Thread t[] = new Thread[2];
		
		t[0] = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < roofData.length;i++) {
					roofData[i] = 0;
				}
				Graphics2D graproof = (Graphics2D)roofImage.getGraphics();
				graproof.clip(new Rectangle(0,0,Constantes.telaW,Constantes.telaH));
				//System.out.println(" "+graproof.getClip());
				tileMap.renderRoof(graproof);
				if(tileMap.isUnderRoof((int)player.position.x,(int)player.position.y)) {
					int cx = Constantes.telaW/2;
					int cy = Constantes.telaH/2;
					int distmax = tmanhoDaluz*tmanhoDaluz;
					for(int i = 0; i < Constantes.telaH;i++) {
						for(int j = 0; j < Constantes.telaW;j++) {
							int dx = j - cx;
							int dy = i - cy;
							int dist = dx*dx+dy*dy;
							if(dist<distmax) {
								roofData[i*Constantes.telaW+j] = 0;
							}
						}
					}
				}
			}
		});
		
		t[1] = new Thread(new Runnable() {
			
			@Override
			public void run() {
				tileMap.pontosdeluz[0][0] = 1;
				tileMap.pontosdeluz[0][1] = Constantes.telaW/2;
				tileMap.pontosdeluz[0][2] = Constantes.telaH/2;
				tileMap.pontosdeluz[0][4] = tmanhoDaluz;
				tileMap.prepareLights();
				//tileMap.prepareLightsMultthread();
			}
		});		
		
		
		t[0].start();
		t[1].start();
		try {
			t[0].join();
			t[1].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		dbg.drawImage(roofImage, 0, 0, null);
		tileMap.renderLigth(dbg);

		dbg.setColor(Color.yellow);
		// systemdata
		drawSystemData(dbg);
	}

	public List<GameObject> getSortedEntities() {
		return sortedEntities;
	}

	public void setSortedEntities(List<GameObject> sortedEntities) {
		this.sortedEntities = sortedEntities;
	}

	public void sortEntities() {

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
			player.LEFT = true;
		}

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.RIGHT = true;
		}

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.UP = true;
		}

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.DOWN = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		int keyCode = e.getKeyCode();
		// player.direction = Direction.STOP;
		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.LEFT = false;
		}

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.RIGHT = false;
		}

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.UP = false;
		}

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.DOWN = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mousePosition.set(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mousePosition.set(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// fix estou sem mouse
		player.FIRE = false;

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		player.FIRE = true;
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
		sb.append(" FPS " + GamePanel.FPS);
		String systemData = sb.toString();

		String objects = String.format("nb-GE:%d", sortedEntities.size());
		g.drawString(systemData, 10, 30);
	}
}
