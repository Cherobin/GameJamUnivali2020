package engine.utils;
 

import java.awt.Graphics2D;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import old.Constantes;
 
 
public class TargetFollower extends AbstractComponent implements Component {
	public GameEntity target;
	public float rotation = 0.0f, tween = 0.0f;
	public BoundingBox boundingBox = new BoundingBox(10, 10, 10, 10);
	public Vector2D offset = new Vector2D(0, 0);
 
	public TargetFollower() {
		this.name = "target_follower";
	}

 
	public TargetFollower(GameEntity target, float rotation, float tween) {
		this();
		this.target = target;
		this.tween = tween;
	}
 
	public TargetFollower(GameEntity target, float rotation, float tween, Vector2D offset) {
		this(target, rotation, tween);
		this.offset = offset;
		initialize(target);
	}

	@Override
	public void initialize(GameObject e) {
		super.initialize(e);
		boundingBox = new BoundingBox(40, 40, Constantes.telaW - 80, Constantes.telaH - 80);
	}
 
	@Override
	public void update(GameObject e, float diffTime) {
		GameEntity ge = (GameEntity) e;
		if (target != null) {
			//if (!target.boundingBox.collide(ge.position)) {
			 
				ge.rotation = (float) Math.atan2(target.position.y - ge.position.y,
						target.position.x - ge.position.x);
				
			//}
		}

	}
}

