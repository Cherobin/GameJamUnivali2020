package engine.base.entities;

import java.awt.Graphics2D;
import java.util.Map;

 
public interface GameState {
 
	public String getName();
 
	public void update(float diffTime); 
	
	public void render(Graphics2D dbg);
 
	public Map<String, GameObject> getEntities();

}
