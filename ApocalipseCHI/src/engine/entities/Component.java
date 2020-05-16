package engine.entities;

import java.awt.Graphics2D;
 
public interface Component {
	 
	public String getName();
 
	public void initialize(GameObject e);

	public void input(GameObject e);
 
	public void updateBefore(GameObject e, float diffTime);

	public void update(GameObject e, float diffTime);
 
	public void updateAfter(GameObject e, float diffTime);

	public void renderBefore(GameObject e, Graphics2D dbg);
 
	public void render(GameObject e, Graphics2D dbg);

	public void renderAfter(GameObject e, Graphics2D dbg);

}
