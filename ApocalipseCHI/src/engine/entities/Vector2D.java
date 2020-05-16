package engine.entities;

/**
 * Internal Class to manipulate simple Vector2D.
 * 
 * @author Frédéric Delorme
 *
 */
public class Vector2D {
	public float x, y;

	public Vector2D() {
		x = 0.0f;
		y = 0.0f;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2D(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Compute distance between this vector and the vector <code>v</code>.
	 * 
	 * @param v
	 *            the vector to compute distance with.
	 * @return
	 */
	public float distance(Vector2D v) {
		float v0 = x - v.x;
		float v1 = y - v.y;
		return (float) Math.sqrt(v0 * v0 + v1 * v1);
	}

	/**
	 * Normalization of this vector.
	 */
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

	/**
	 * Add the <code>v</code> vector to this vector.
	 * 
	 * @param v
	 *            the vector to add to this vector.
	 * @return this.
	 */
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	/**
	 * Multiply vector by a factor.
	 * 
	 * @param factor
	 *            the factor to multiply the vector by.
	 * @return this.
	 */
	public Vector2D multiply(float factor) {
		x = x * factor;
		y = y * factor;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vector2D [x=").append(x).append(", y=").append(y).append("]");
		return builder.toString();
	}
}
