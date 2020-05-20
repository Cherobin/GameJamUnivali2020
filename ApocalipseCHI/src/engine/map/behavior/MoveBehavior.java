package engine.map.behavior;

import java.awt.Rectangle;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.utils.Direction;
import engine.utils.Vector2D;
 

public class MoveBehavior extends AbstractComponent implements Component {
 
	 
	public Vector2D speed = new Vector2D(0, 0);
 
	public Vector2D accel = new Vector2D(0, 0);
  
 
	public float maxSpeed = 100f;

	public MoveBehavior(Vector2D speed, Vector2D accel) {
		this.name = "move_behavior";
		this.speed = speed;
		this.accel = accel;
	}
	
	public void update(GameObject e, float diffTime) {
		GameEntity ge = (GameEntity) e;
		 
 
		ge.oldPosition.set(ge.position);
		
		if (Math.abs(ge.speed.x) > maxSpeed) {
			ge.speed.x = maxSpeed * Math.signum(ge.speed.x);
		}
		if (Math.abs(ge.speed.y) > maxSpeed) {
			ge.speed.y = maxSpeed * Math.signum(ge.speed.x);
		} 
		
		ge.position.x +=  ge.speed.x * Math.cos(ge.rotation)  * diffTime / 1000f;
		ge.position.y += ge.speed.y * Math.sin(ge.rotation) * diffTime / 1000f;

	}

 
	private GameEntity constraints(GameObject e, Rectangle viewport) {
		GameEntity ge = (GameEntity) e;
		if (ge.position.x + ge.offset.x <= viewport.x) {
			ge.position.x = viewport.x - ge.offset.x;
			ge.accel.x = 0;
			ge.speed.x = 0;
		}
		if (ge.position.y + ge.offset.y <= viewport.y) {
			ge.position.y = viewport.y - ge.offset.y;
			ge.speed.y = 0;
			ge.accel.y = 0;
		}
		if (ge.position.x + ge.width + ge.offset.x >= viewport.x + viewport.width) {
			ge.position.x = viewport.x + viewport.width - (ge.width + ge.offset.x);
			ge.accel.x = 0;
			ge.speed.x = 0;
		}
		if (ge.position.y + ge.height + ge.offset.y >= viewport.y + viewport.height) {
			ge.position.y = viewport.y + viewport.height + (ge.height + ge.offset.y);
			ge.accel.y = 0;
			ge.speed.y = 0;
		}
		return ge;
	}
}