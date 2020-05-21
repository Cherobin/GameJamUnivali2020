package engine.base.entities;

import java.awt.Graphics2D;
 
public interface Component {
	 
	public String getName();

	public void input(GameObject e);
	
	public void update(GameObject e, float diffTime);
 
	public void render(GameObject e, Graphics2D dbg); 

}
