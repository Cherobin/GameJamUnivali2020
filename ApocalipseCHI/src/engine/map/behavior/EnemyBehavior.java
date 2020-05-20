package engine.map.behavior;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
 

public class EnemyBehavior extends AbstractComponent implements Component {

	public enum EnemyState {
		STOP, DETECT, PURSUIT
	}
 
	GameEntity target;
	float speed;
	float sensorDistance;
	float viewDistance;

	boolean sensorFlag = false, viewFlag = false;
	boolean debug = true;
	Color color = Color.GREEN;
	EnemyState state = EnemyState.STOP, previousState = EnemyState.STOP;

	public EnemyBehavior(GameEntity target, float speed, float sensorDistance, float viewDistance) {
		super();
		this.name = "enemy_behavior";
		this.target = target;
		this.speed = speed;
		this.sensorDistance = sensorDistance;
		this.viewDistance = viewDistance;
	}

 
	@Override
	public void update(GameObject e, float diffTime) {
		super.update(e, diffTime);
	 
		GameEntity ge = (GameEntity) e;
		 
		if(ge.inCollider) {
			ge.position.set(ge.oldPosition);
    	}
		
		ge.oldPosition.set(ge.position);
	
		
		EnemyState state = evaluate(ge, target);
		
		if (!previousState.equals(state)) {
			switch (state) {
			case DETECT:
			    ge.speed.y = ge.speed.x = +speed;
			    ge.FIRE = false;
				color = Color.RED;
				break;
			case PURSUIT:
				ge.speed.y = ge.speed.x = +speed;
				color = Color.ORANGE;
				ge.FIRE = true;
				break;
			case STOP:
				ge.speed.y = ge.speed.x = 0.0f; 
				color = Color.GREEN;
				ge.FIRE = false;
				break;
			}
			previousState = state;
		}
		 
		ge.position.x += ge.speed.x * Math.cos(ge.rotation)  * diffTime / 1000f;
		ge.position.y += ge.speed.y * Math.sin(ge.rotation) * diffTime / 1000f;
		
	}


	private EnemyState evaluate(GameEntity ge, GameEntity target) {
		float distanceToTarget = target.position.distance(ge.position);
		
		//System.out.println("state: " + state + " " + distanceToTarget + " sensor " +sensorDistance + " view " + viewDistance);
	
		switch (state) {
		case STOP:
			if (distanceToTarget < sensorDistance) {
				state = EnemyState.DETECT; 
			}
			break;
		case DETECT:
			if (distanceToTarget <= viewDistance - 20) {
				state = EnemyState.PURSUIT; 
			} else if (distanceToTarget >= sensorDistance) {
				state = EnemyState.STOP; 
			}
			break;
		case PURSUIT:
			if (distanceToTarget > sensorDistance || distanceToTarget >= viewDistance) {
				state = EnemyState.STOP; 
			}
			break;
		}

		return state;
	}
	

	@Override
	public void render(GameObject e, Graphics2D dbg) {
		super.render(e, dbg);
		
		GameEntity ge = (GameEntity) e;
		if(state == EnemyState.DETECT || state == EnemyState.PURSUIT) {
			ge.rotation = (float) Math.atan2(target.position.y - ge.position.y,
					target.position.x - ge.position.x);
		}
	}
}