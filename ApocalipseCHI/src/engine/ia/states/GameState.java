/**
 * SnapGames
 * @year 2016
 */
package engine.ia.states;

import java.awt.Graphics2D;
import java.util.Map;

import engine.core.game.GameStateManager;

 
public interface GameState extends State {
 
	public String getName();
 
	public void setStateManager(GameStateManager gsm);
 
	public void initialize(); 
 
	public void update(float diffTime); 
	
	public void render(Graphics2D dbg);
 
	public Map<String, engine.entities.GameObject> getEntities();

}
