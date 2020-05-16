package apiPS;
public class Console {
	/*
	 * DEBUG_LEVEL = 0 -> nothing
	 * DEBUG_LEVEL = 1 -> error
	 * DEBUG_LEVEL = 2 -> warning
	 * DEBUG_LEVEL = 3 -> debug
	 */
	public static final int DEBUG_LEVEL = 1;

	public static void debug (String x) {
		if (DEBUG_LEVEL >= 3) System.out.println (x);
	}
	public static void debug (int x) {
		debug (String.valueOf(x));
	}
	public static void warning (String x) {
		if (DEBUG_LEVEL >= 2) System.out.println (x);
	}
	public static void warning (int x) {
		warning (String.valueOf(x));
	}
	public static void error (String x) {
		if (DEBUG_LEVEL >= 1) System.out.println (x);
	}
	public static void error (int x) {
		error (String.valueOf (x));
	}

	// always print something
	public static void print (String x) {
		System.out.print (x);
	}
	public static void println (String x) {
		Console.debug (x);
	}
}
