/**
 * SnapGames
 * @year 2016
 */
package engine.ia.states;

import java.awt.Graphics2D;
import java.util.Map;

import engine.base.entities.GameObject;

 
public interface GameState {
 
	public String getName();
 
	public void update(float diffTime); 
	
	public void render(Graphics2D dbg);
 
	public Map<String, GameObject> getEntities();

}
