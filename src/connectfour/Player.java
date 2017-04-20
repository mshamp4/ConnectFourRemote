package connectfour;

public enum Player {
	BLACK, RED, NONE;

	public Player next() {
		return this == BLACK ? RED : BLACK;
	}
}
