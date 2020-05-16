package engine.ia.states;
 

import java.awt.Graphics2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
 
public class GameStateManager {

 
	Map<String, Class<? extends GameState>> stateClasses = new ConcurrentHashMap<String, Class<? extends GameState>>();
 
	Map<String, GameState> states = new ConcurrentHashMap<String, GameState>();

 
	GameState currentState = null;
	private GameState previousState;

	private String startState = null;
 
	public GameStateManager() {
		super(); 
	}

 
	public void add(GameState state) {
		state.setStateManager(this);
		states.put(state.getName(), state);
	}

 
	private GameState create() {
		//TODO 
		GameState state = null;
		state.setStateManager(this);
		states.put(state.getName(), state);
		return states.get(state.getName());
	}
 

 
 
	public void update(float delta) {
		if (this.currentState != null) {
			updateState(delta);
		} 
	}

	 
	private void updateState(float diffTime) {
	 
	}
 

 
	public void render(Graphics2D g) {

	}

	public GameState getActiveState() {
		return currentState;
	}

}

