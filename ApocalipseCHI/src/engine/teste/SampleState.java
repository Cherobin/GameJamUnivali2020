/**
 * SnapGames
 * @year 2016
 */
package engine.teste;

import java.awt.Graphics2D;

import engine.entities.GameObject;
import engine.ia.states.FSM;
import engine.ia.states.GameState;
import engine.ia.states.GameStateManager;
import engine.map.TileMap;
import engine.map.TileMapLoader;
import tiled.core.Map;

 
public class SampleState implements GameState {

	private SampleEntityBuilder seb;
	private TileMapLoader tml;

	private TileMap tileMap = null;
	
	public SampleState() {
		initialize(); 
	}
  
	@Override
	public void initialize() {
		
		//TODO Inicializar o som
		 
		tileMap = new TileMap("tilemap");

		seb = new SampleEntityBuilder();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStateManager(GameStateManager gsm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float diffTime) {
		// TODO Auto-generated method stub'
		if(tileMap!=null)
			tileMap.update(diffTime);
	}

	@Override
	public void render(Graphics2D dbg) {
		// TODO Auto-generated method stub
		if(tileMap!=null)
			tileMap.render(dbg);
		
	}

	@Override
	public java.util.Map<String, GameObject> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}
}
