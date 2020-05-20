package engine.base.entities;

import java.awt.Graphics2D;
import java.util.Map;

import engine.utils.BoundingBox;
 
 
public interface GameObject extends Comparable<GameObject> {
  
	String getName();
 
	void update(float diffTime);
 
	void render(Graphics2D dbg);
 
	Map<String, Component> getComponents();

	int compareTo(GameObject o); 
	
	BoundingBox getBoundingBox();
 
	Map<String, Object> getProperties();
	
	boolean isAlive();
}