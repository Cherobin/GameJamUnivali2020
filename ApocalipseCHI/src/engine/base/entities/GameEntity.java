package engine.base.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import engine.utils.BoundingBox;
import engine.utils.CollisionType;
import engine.utils.Direction;
import engine.utils.Vector2D; 

public class GameEntity implements GameObject {
 
	public String name = ""; 
	public Color color = Color.WHITE; 
	public int priority = 1;
  
	public int layer = 1; 
	public Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
 
	public Map<String, Component> components = new ConcurrentHashMap<String, Component>();
 
	public Vector2D position = new Vector2D(0, 0); 
	public Vector2D offset = new Vector2D(0, 0); 
	public Vector2D speed = new Vector2D(0, 0); 
	public Vector2D accel = new Vector2D(0, 0); 
	public int width = 0, height = 0; 
	public float rotation; 
	public float maxSpeed = 2.3f;

	public Vector2D collisionBox = new Vector2D(width, height);
	public CollisionType collitionType = CollisionType.BoundingBox;
	public Direction direction = null;

	public BoundingBox boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 0.0f);

 
	//DEBUG
	public boolean showData = true; 
	public boolean drawBoundingBox = true; 
	public boolean debug = true; 
	
	
	
	public GameEntity(String name, Vector2D position, int width, int height, float rotation) {
		this(name);
		this.position = position;
		this.width = width;
		this.height = height;
		boundingBox = new BoundingBox(0, 0, width, height);

		this.rotation = rotation;
	}

	public GameEntity(String name, Vector2D pos, int w, int h, int r, Vector2D s, Vector2D a) {
		this(name, pos, w, h, 0.0f);
		this.speed = s;
		this.accel = a;
	}

	public GameEntity(String name) {
		super();
		this.name = name; 
		this.direction = Direction.STOP;
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

	} 
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		int xx = (int) (position.x + offset.x);
		int yy = (int) (position.y + offset.y);
		g.fillRect(xx, yy, this.width, this.height);

		if (debug && this.showData) {
			showMetaData(g, xx, yy);
		}
	}
 
	protected void showMetaData(Graphics2D g, float x, float y) {
		int xx = (int) x;
		int yy = (int) y;
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
		g.drawLine((int) (xx - offset.x), (int) (yy - offset.y), (int) (xx - offset.x + (accel.x * 2000)),
				(int) (yy - offset.y + (accel.y * 2000)));
		g.setStroke(strk);
		g.setColor(Color.YELLOW);
		g.drawString(String.format("entity(%s)", name), position.x + 20, position.y - (4 * fh));
		g.drawString(String.format("D(%s)", this.direction), position.x + 20, position.y - (3 * fh));
		g.drawString(String.format("P(%.01f,%.01f) x1", position.x, position.y), position.x + 20,
				position.y - (2 * fh));
		g.drawString(String.format("S(%.01f,%.01f) x1000", speed.x * 1000, speed.y * 1000), position.x + 20,
				position.y - fh);
		g.drawString(String.format("A(%.01f,%.01f) x1000", accel.x * 1000, accel.y * 1000), position.x + 20,
				position.y);
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
	public void initialize() {

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
				.append(", accel=").append(accel).append(", boundingBox=").append(boundingBox).append(", collisionBox=")
				.append(collisionBox).append(", collitionType=").append(collitionType).append(", direction=")
				.append(direction).append("]");
		return builder.toString();
	}
}