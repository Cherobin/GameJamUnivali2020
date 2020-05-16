/**
 * SnapGames
 * 
 * @year 2016
 * 
 */
package engine.entities;

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
	public void initialize(GameObject e) {
	}
	
	@Override
	public void input(GameObject e) {
	}

	@Override
	public void updateBefore(GameObject e, float diffTime) {
	}

	@Override
	public void update(GameObject e, float diffTime) {

	}

	@Override
	public void updateAfter(GameObject e, float diffTime) {
	}

	@Override
	public void renderBefore(GameObject e, Graphics2D dbg) {
	}

	@Override
	public void render(GameObject e, Graphics2D dbg) {
	}

	@Override
	public void renderAfter(GameObject e, Graphics2D dbg) {
	}
}
