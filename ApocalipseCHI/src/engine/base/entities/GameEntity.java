package engine.base.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import engine.core.game.CanvasGame;
import engine.map.behavior.EnemyBehavior.EnemyState;
import engine.utils.BoundingBox;
import engine.utils.CollisionType;
import engine.utils.Vector2D; 

public class GameEntity implements GameObject {
 
	public enum GameEntityType {
		ENEMY, FIRE, PLAYER
	}
	
	public String name = ""; 
	public Color color = Color.WHITE; 
	public int priority = 1;
	public boolean alive;
	public boolean inCollider;
	public int layer = 1; 
	public Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
 
	public GameEntityType type; 
	
	public Map<String, Component> components = new ConcurrentHashMap<String, Component>();
 
	public Vector2D position = new Vector2D(0, 0); 
	public Vector2D offset = new Vector2D(0, 0); 
	public Vector2D speed = new Vector2D(0, 0);  
	public Vector2D oldPosition = new Vector2D(0, 0); 
	public boolean chegouObjetivo;
	public int indexPathFind = 0;
	EnemyState state;
	public int width = 0, height = 0; 
	public float rotation; 
	public float maxSpeed = 100f;
	public int life;
	public int maxLife;
	public Vector2D collisionBox = new Vector2D(width, height);
	public CollisionType collitionType = CollisionType.BoundingBox;
	
	public boolean RIGHT; 
	public boolean LEFT;
	public boolean UP;
	public boolean DOWN;
	public boolean FIRE;

	public BoundingBox boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f);

	public int damage = 0;
 
	//DEBUG
	public boolean showData = false; 
	public boolean drawBoundingBox = false; 
	public boolean debug = false; 
	
	
	public enum GameEntityTypeAnimation {
		PRIMARY_WP, SECONDARY_WP, MELEE_WP, MELEE_ATACK, IDLE
	}
	
	public int charsetX, charsetY;
	public GameEntityTypeAnimation animationType;
	public  float frameTime = 100;
	public  float timer = 0;
	public int animation = 0;
	public  int frame = 0;
	
	public GameEntity(String name, Vector2D position, int width, int height, float rotation) {
		this(name);
		this.position = position;
		this.width = width;
		this.height = height;
		boundingBox = new BoundingBox(0, 0, width, height);
		this.alive = true;
		this.inCollider = false;
		this.rotation = rotation;
		this.chegouObjetivo = false;
		this.maxLife = life = 100; 
		
	}

	public GameEntity(String name, Vector2D pos, int w, int h, int r, Vector2D s) {
		this(name, pos, w, h, 0.0f);
		this.speed = s; 
		this.alive = true;
		this.chegouObjetivo = false;
		this.maxLife = life = 100; 
	}

	public GameEntity(String name) {
		super();
		this.name = name;  
		RIGHT = false;
		LEFT = false;
		UP = false;
		DOWN = false;
		alive = true;
		this.alive = true;
		this.chegouObjetivo = false;
		this.maxLife = life = 100; 
	}

	public GameEntity( String name, Component... components) {
		this(name);
		addComponent(components);
	}

	public void addComponent(Component... components) {
		for (Component cmp : components) {
			this.components.put(cmp.getName(), cmp);
		}
	} 
	
	@Override
	public void update(float dt) {
		boundingBox.x = position.x;
		boundingBox.y = position.y;
		
		if(life < 0) {
			alive = false;
		}
		
	} 
	
	@Override
	public void render(Graphics2D dbg) {
		AffineTransform t = dbg.getTransform();

		dbg.translate(position.x - CanvasGame.tileMap.getTelaX(), position.y  - CanvasGame.tileMap.getTelaY());
		
		if (debug && this.showData) {
			dbg.setColor(color); 
			dbg.fillRect(0, 0, this.width, this.height);
			showMetaData(dbg);
		}
		
		dbg.setTransform(t);
	}
 
	protected void showMetaData(Graphics2D g) {
		int xx = (int) 10;
		int yy = (int) 10;
		
		int fh = g.getFontMetrics().getHeight();
		if (drawBoundingBox) {
			g.setColor(Color.ORANGE);
			g.drawRect((int) (boundingBox.x + offset.x), (int) (boundingBox.y + offset.y), (int) boundingBox.w,
					(int) boundingBox.h);
		}
		g.setColor(Color.BLUE);
		// g.drawRect(xx, yy, this.width, this.height);
		g.fillArc((int) (xx - offset.x - 4), (int) (yy - offset.y - 4), 8, 8, 0, 360);

		Stroke strk = g.getStroke();
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f));
		g.drawLine((int) (xx - offset.x), (int) (yy - offset.y), (int) (xx - offset.x + (speed.x * 100)),
				(int) (yy - offset.y + (speed.y * 100)));
		g.setColor(Color.GREEN);
		g.setStroke(strk);
		g.setColor(Color.YELLOW);
		g.drawString(String.format("entity(%s)", name), position.x + 20, position.y - (4 * fh));
		
		//RIGHT = false;
		//LEFT = false;
		//UP = false;
		//DOWN = false;
		g.drawString(String.format("D(%s)", RIGHT+" "+LEFT+" "+UP+" "+DOWN), position.x + 20, position.y - (3 * fh));
		
		g.drawString(String.format("P(%.01f,%.01f) x1", position.x, position.y), position.x + 20,
				position.y - (2 * fh));
		g.drawString(String.format("S(%.01f,%.01f) x1000", speed.x * 1000, speed.y * 1000), position.x + 20,
				position.y - fh);
		g.drawString(String.format("R(%.02f)", rotation), position.x + 20, position.y + fh);
	}
 
	public Component getComponent(String name) {
		return components.get(name);
	}
 
	public void addComponents(List<Component> cmps) {
		for (Component c : cmps) {
			components.put(c.getName(), c);
		}
	}
 
	@Override
	public int compareTo(GameObject o) {
		GameEntity go = (GameEntity) o;
		return (go.layer == layer ? (go.priority == priority ? 0 : (go.priority > priority ? 1 : -1))
				: (go.layer > layer ? 1 : -1));
	}

 
	@Override
	public Map<String, Component> getComponents() {
		return components;
	}

 
	@Override
	public String getName() {
		return name;
	}
 
	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
 
	@Override
	public Map<String, Object> getProperties() { 
		return null;
	}
 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameEntity [").append("name=").append(name).append(", color=").append(color)
				.append(", priority=").append(priority).append(", layer=").append(layer).append(", width=")
				.append(width).append(", height=").append(height).append(", rotation=").append(rotation)
				.append(", showData=").append(showData).append(", properties=").append(properties)
				.append(", components=").append(components).append(", position=").append(position).append(", offset=")
				.append(offset).append(", speed=").append(speed).append(", maxSpeed=").append(maxSpeed)
				.append(", boundingBox=").append(boundingBox).append(", collisionBox=")
				.append(collisionBox).append(", collitionType=").append(collitionType).append(", direction=")
				.append(RIGHT).append("]"); // TODO DEPOIS TEM QUE ACERTAR
		return builder.toString();
	}

	@Override
	public boolean isAlive() {
		return alive;	
	}
}