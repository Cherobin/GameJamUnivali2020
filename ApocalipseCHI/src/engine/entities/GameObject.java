package engine.entities;

import java.awt.Graphics2D;
import java.util.Map;

import engine.utils.BoundingBox;
 
 
public interface GameObject extends Comparable<GameObject> {
 
	String getName();
 
	void update(float dt);
 
	void render(Graphics2D dbg);
 
	void initialize(); 
	
	Map<String, Component> getComponents();

	int compareTo(GameObject o); 
	
	BoundingBox getBoundingBox();
 
	Map<String, Object> getProperties();

}