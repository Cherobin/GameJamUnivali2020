package engine.map.behavior;

import java.awt.Rectangle;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.utils.Vector2D;
 

public class MoveElementBehavior extends AbstractComponent implements Component {
 
	  
	public Vector2D accel = new Vector2D(0, 0);
  
 
	public float maxSpeed = 100f;

	public MoveElementBehavior(Vector2D accel) {
		this.name = "move_behavior";
		this.accel = accel; 
	}
	
	public void update(GameObject e, float diffTime) {
		GameEntity ge = (GameEntity) e;
		  
	
		
		ge.position.x +=  ge.speed.x * Math.cos(ge.rotation)  * diffTime / 1000f;
		ge.position.y += ge.speed.y * Math.sin(ge.rotation) * diffTime / 1000f;
		
		 
		
		if(ge.inCollider) {
			ge.position.set(ge.oldPosition);
		}
	}

 
	private GameEntity constraints(GameObject e, Rectangle viewport) {
		GameEntity ge = (GameEntity) e;
		if (ge.position.x + ge.offset.x <= viewport.x) {
			ge.position.x = viewport.x - ge.offset.x;
			ge.speed.x = 0;
		}
		if (ge.position.y + ge.offset.y <= viewport.y) {
			ge.position.y = viewport.y - ge.offset.y;
			ge.speed.y = 0; 
		}
		if (ge.position.x + ge.width + ge.offset.x >= viewport.x + viewport.width) {
			ge.position.x = viewport.x + viewport.width - (ge.width + ge.offset.x);
			ge.speed.x = 0;
		}
		if (ge.position.y + ge.height + ge.offset.y >= viewport.y + viewport.height) {
			ge.position.y = viewport.y + viewport.height + (ge.height + ge.offset.y);
			ge.speed.y = 0;
		}
		return ge;
	}
}