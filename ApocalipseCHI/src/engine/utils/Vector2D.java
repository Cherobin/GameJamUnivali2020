package engine.utils;
 
public class Vector2D {
	public float x, y;

	public Vector2D() {
		x = 0.0f;
		y = 0.0f;
	}

 
	public Vector2D(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

 
	public float distance(Vector2D v) {
		float v0 = x - v.x;
		float v1 = y - v.y;
		return (float) Math.sqrt(v0 * v0 + v1 * v1);
	}
 
	public void normalize() {
		// sets length to 1
		//
		double length = Math.sqrt(x * x + y * y);

		if (length != 0.0) {
			float s = 1.0f / (float) length;
			x = x * s;
			y = y * s;
		}
	}

 
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

 
	public Vector2D multiply(float factor) {
		x = x * factor;
		y = y * factor;
		return this;
	}

 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vector2D [x=").append(x).append(", y=").append(y).append("]");
		return builder.toString();
	}
}
