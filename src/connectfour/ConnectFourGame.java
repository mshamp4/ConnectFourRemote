package connectfour;

/**
 * This class is the controller of the MVC design, it creates a fully 
 * functional connect four game board and makes all decisions 
 * regarding the state of the game.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public class ConnectFourGame {
	
	private cfCell[][] board;
	
	private GameStatus gameStatus;
	
	private Player player;
	
	private final int DEFAULT_ROW = 6;
	
	private final int DEFAULT_COL = 7;
	
	private int rating;
	
	public ConnectFourGame(final int row, final int col) {
		setGameStatus(GameStatus.NotOverYet);
		player = Player.BLACK;
		board = new cfCell[row][col];
		initialize();
	}
	
	private void initialize() {
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				board[row][col] = new cfCell();
			}
		}
	}
	
	public int selectcfCell(int row, final int col, Player player) {
		if (getCell(row, col).isMarked() || getCell(row, col) == null) {
			return -1;
		}
		while (!getCell(row, col).isMarked() && getCell(row, col) != null) {
			row++;
		}
		board[row-1][col].setMarked(true);
		board[row-1][col].setPlayer(player);
		player = player.next();
		return 1;
	}
	
	public boolean checkStatus() {
		
		return true;
	}
	
	public cfCell getCell(final int row, final int col) {
		return (row < 0 || col < 0 || row >= getDEFAULT_ROW()
				|| col >= getDEFAULT_COL()) ? null : board[row][col];
	}
	
	public cfCell[][] getBoard() {
		return board;
	}

	public void setBoard(cfCell[][] board) {
		this.board = board;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getDEFAULT_ROW() {
		return DEFAULT_ROW;
	}

	public int getDEFAULT_COL() {
		return DEFAULT_COL;
	}
}

