package engine.map.behavior;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.base.entities.AbstractComponent;
import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.core.game.CanvasGame;
import engine.map.AEstrela;
import engine.map.TileMap;

public class EnemyBehavior extends AbstractComponent implements Component {

	public enum EnemyState {
		STOP, DETECT, PURSUIT, PATHFIND, GOPATHFIND
	}

	GameEntity target;
	float speed;
	float sensorDistance;
	float viewDistance;
	public AEstrela aestrela;
	boolean sensorFlag = false, viewFlag = false;
	boolean debug = true;
	Color color = Color.GREEN;
	EnemyState state = EnemyState.STOP, previousState = EnemyState.STOP;
	TileMap tilemap;
	public int caminho[] = null;
	int nodosabertos = 0;
	// EnemyState state = EnemyState.PATHFIND, previousState = EnemyState.PATHFIND;

	public EnemyBehavior(GameEntity target, float speed, float sensorDistance, float viewDistance, TileMap tilemap) {
		super();
		this.name = "enemy_behavior";
		this.target = target;
		this.speed = speed;
		this.sensorDistance = sensorDistance;
		this.viewDistance = viewDistance;
		this.tilemap = tilemap;
		aestrela = new AEstrela(tilemap);
	}

	@Override
	public void update(GameObject e, float diffTime) {
		super.update(e, diffTime);

		GameEntity ge = (GameEntity) e;

		EnemyState state = evaluate(ge, target);

		if (ge.inCollider && state != EnemyState.GOPATHFIND) {
			ge.position.set(ge.oldPosition);
			state = EnemyState.PATHFIND; 
		}

		ge.oldPosition.set(ge.position);

		if (!previousState.equals(state)) {
		//	System.out.println("state: " + state);
			switch (state) {
			case PATHFIND:
				setaObjetivo(ge, (int) CanvasGame.player.position.x, (int) CanvasGame.player.position.y);
				break;
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
			case GOPATHFIND:
				//System.out.println("GOPATHFIND");
				break;
			}
			previousState = state;
		}

		ge.position.x += ge.speed.x * Math.cos(ge.rotation) * diffTime / 1000f;
		ge.position.y += ge.speed.y * Math.sin(ge.rotation) * diffTime / 1000f;

	 
	}

	public void setaObjetivo(GameEntity ge, int objetivox, int objetivoy) { 
		caminho = aestrela.StartAestrela((int) (ge.position.x), (int) (ge.position.y),
				objetivox, objetivoy, 500);
		state = EnemyState.GOPATHFIND;
		ge.chegouObjetivo = false;
		ge.indexPathFind = 0;
	}

	boolean teste = true;
	int patXX = 0;
	int patYY = 0;
	void goPath(GameEntity ge) {
		if(caminho == null) {
			return;
		}
		if (ge.indexPathFind < caminho.length / 2) {
			if(teste) {
				patXX = caminho[ge.indexPathFind * 2];
				patYY= caminho[ge.indexPathFind * 2 + 1];
				ge.position.set(patXX,patYY ); 
			 	teste = false;
			} 
			
			if (ge.position.x == patXX && patYY == ge.position.y) { 
				ge.indexPathFind++;
				teste = true;
			}

		} else {
			ge.chegouObjetivo = true;
			state = EnemyState.STOP; 
		}

	}

	private EnemyState evaluate(GameEntity ge, GameEntity target) {
	 
		float distanceToTarget = target.position.distance(ge.position);
 
		switch (state) {
		case PATHFIND:
			//System.out.println("RODA PATHFIND");
			break;
		case GOPATHFIND:
			if (ge.chegouObjetivo) {
				state = EnemyState.STOP;
			} else {
				goPath(ge);
			}
			break;
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
		default:
			System.out.println("ia dando pau! :D");
			break;
		}

		return state;
	}


	@Override
	public void render(GameObject e, Graphics2D dbg) {
		super.render(e, dbg);

		GameEntity ge = (GameEntity) e;
		
		AffineTransform t = dbg.getTransform();
 
		
		if ((state == EnemyState.DETECT || state == EnemyState.PURSUIT) || state == EnemyState.GOPATHFIND) {
			ge.rotation = (float) Math.atan2(target.position.y - ge.position.y, target.position.x - ge.position.x);
		}
	 
		//desenha caminho
		/*
		if (caminho != null) {
			for (int i = 0; i < caminho.length / 2; i++) {
				
				int patX = caminho[i * 2] ;
				int patY = caminho[i * 2 + 1] ;
			
				dbg.translate(patX - tilemap.getTelaX(), patY - tilemap.getTelaY());
				dbg.setColor(Color.black);
				dbg.fillRect(0, 0, 16, 16);
				dbg.setTransform(t);
				
			}
		}
		*/
		
		
	}
}