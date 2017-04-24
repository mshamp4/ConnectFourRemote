package connectfour;

public class Move {

	private int row;
	
	private int col;
	
	private int rating;
	
	private Player player;
	
	public Move(final int row, final int col, final Player player) {
		setRow(row);
		setCol(col);
		setPlayer(player);
		setRating(0);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	
}
