/**
 * SnapGames
 * 
 * @year 2016
 * 
 */
package engine.base.entities;

import java.awt.Graphics2D;
 
 
public abstract class AbstractComponent implements Component {

	protected String name = "";

	public AbstractComponent() {
		this.name = "";
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void input(GameObject e) {
	}

	@Override
	public void update(GameObject e, float diffTime) {

	} 

	@Override
	public void render(GameObject e, Graphics2D dbg) {
	}

	 
}
