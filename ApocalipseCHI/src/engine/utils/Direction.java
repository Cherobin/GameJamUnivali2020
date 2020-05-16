package engine.utils;

 
public enum Direction {
	UP_RIGHT, DOWN_RIGHT, RIGHT, UP_LEFT, DOWN_LEFT, LEFT, UP, DOWN, STOP, UNKNOWN;

	public static Direction oposite(Direction current) {
		if (current != null) {
			switch (current) {
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case DOWN_RIGHT:
				return UP_LEFT;
			case DOWN_LEFT:
				return UP_RIGHT;
			case UP_LEFT:
				return DOWN_RIGHT;
			case UP_RIGHT:
				return DOWN_LEFT;
			default:
				return STOP;
			}
		} else {
			return STOP;
		}
	}
}
