package engine.utils;

public class BoundingBox {

	public float x, y, w, h;

	public BoundingBox(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoundingBox [x=").append(x).append(", y=").append(y).append(", w=").append(w).append(", h=")
				.append(h).append("]");
		return builder.toString();
	}
 
	public boolean collide(Vector2D position) {
		if (position.x > x 
				&& position.x <= x + w 
				&& position.y > y 
				&& position.y <= y + h) {
			return true;
		}
		return false;
	}
 
	public boolean collide(BoundingBox box) {
		if ((box.x >= x + w) || (box.x + box.w <= x) || (box.y >= y + h) || (box.y + box.h <= y)) {
			return false;
		} else {
			return true;
		}

	}

}
