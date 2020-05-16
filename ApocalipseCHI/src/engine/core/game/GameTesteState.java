/**
 * SnapGames
 * @year 2016
 */
package engine.core.game;

import java.awt.Graphics2D;

import com.sun.org.apache.bcel.internal.generic.FMUL;

import engine.entities.GameObject;
import engine.ia.states.FSM;
import engine.ia.states.GameState;
import engine.ia.states.GameStateManager;
import engine.map.TileMap;
import engine.map.TileMapLoader;
import tiled.core.Map;

 
public class GameTesteState implements GameState {

	private GameEntityBuilder seb;
	private TileMapLoader tml;
	private GameStateManager gsm;
	private TileMap tileMap = null;
	
	public GameTesteState() {
		initialize(); 
	}
  
	@Override
	public void initialize() {
		tileMap = new TileMap("tilemap");
		gsm = new GameStateManager();
		seb = new GameEntityBuilder();
		tml = new TileMapLoader(seb);
		Map map = null;
		map = tml.load(this, "res/maps/level-1-1.tmx");
		if (map != null) {
			tileMap.setMap(map);
			tileMap.initialize();
		}
	}

	@Override
	public boolean evaluate(FSM fsm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() { 
		return this.getName();
	}

	@Override
	public void setStateManager(GameStateManager gsm) { 
		this.gsm = gsm;
	}

	@Override
	public void update(float diffTime) { 
		if(tileMap!=null)
			tileMap.update(diffTime);
	}

	@Override
	public void render(Graphics2D dbg) { 
		if(tileMap!=null)
			tileMap.render(dbg);
		
	}

	@Override
	public java.util.Map<String, GameObject> getEntities() {  
		return getEntities();
	}
}
