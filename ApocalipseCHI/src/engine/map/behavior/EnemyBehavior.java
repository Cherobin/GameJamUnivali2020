package engine.map.behavior;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
 

public class EnemyBehavior extends AbstractComponent implements Component {

	enum EnemyState {
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
		this.target = target;
		this.speed = speed;
		this.sensorDistance = sensorDistance;
		this.viewDistance = viewDistance;
	}

 
	@Override
	public void update(GameObject e, float diffTime) {
		super.update(e, diffTime);
	
		//System.out.println(e.getName() + " " + state);
		
		GameEntity ge = (GameEntity) e;
		EnemyState state = evaluate(ge, target);
		if (!previousState.equals(state)) {
			switch (state) {
			case DETECT:
				if (ge.position.x - 20 < target.position.x) {
					ge.accel.x = +speed;
				} else if (ge.position.x + 20 > target.position.x) {
					ge.accel.x = -speed;
				} 
				color = Color.RED;
				break;
		 
			case PURSUIT:
				if (ge.position.x - 20 < target.position.x) {
					ge.accel.x = +speed;
				} else if (ge.position.x + 20 > target.position.x) {
					ge.accel.x = -speed;
				} 
				color = Color.ORANGE;
				break;
			case STOP:
				ge.accel.x = 0.0f; 
				color = Color.GREEN;
				break;
			}
			previousState = state;
		}
	}

	@Override
	public void render(GameObject e, Graphics2D dbg) {
		super.render(e, dbg);
 
		 
			// rendering of some visual debug information when debug mode is on.
			GameEntity ge = (GameEntity) e;
			 
			
			// SensorDistance
			dbg.setColor(color);
			dbg.drawArc((int) (ge.position.x - sensorDistance), (int) (ge.position.y - sensorDistance),
					(int) sensorDistance * 2, (int) sensorDistance * 2, 0, 360);
			dbg.drawString("sensor:" + (sensorFlag ? "on" : "off"), (int) (ge.position.x - sensorDistance),
					ge.position.y);
			// ViewDistance
			dbg.setColor(Color.GREEN);
			dbg.drawArc((int) (ge.position.x - viewDistance), (int) (ge.position.y - viewDistance),
					(int) viewDistance * 2, (int) viewDistance * 2, 0, 360);
			dbg.drawString("view:" + (viewFlag ? "on" : "off"), (int) (ge.position.x - viewDistance), ge.position.y);
			dbg.setColor(Color.WHITE);
		 
	 
	}

	private EnemyState evaluate(GameEntity ge, GameEntity target) {
		float distanceToTarget = target.position.distance(ge.position);
		switch (state) {
		case STOP:
			if (distanceToTarget < sensorDistance) {
				state = EnemyState.DETECT;
				//TODO PLAY SOUND
			}
			break;
		case DETECT:
			if (distanceToTarget >= sensorDistance && distanceToTarget <= viewDistance) {
				state = EnemyState.PURSUIT;
			}
			break;
		case PURSUIT:
		default:
			if (distanceToTarget > viewDistance) {
				state = EnemyState.STOP;
			}
			break;
		}

		return state;
	}
}