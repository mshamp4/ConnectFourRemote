package connectfour;

public enum Player {
	BLUE, RED, NONE;

	public Player next() {
		return this == BLUE ? RED : BLUE;
	}
}
