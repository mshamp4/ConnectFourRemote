package connectfour;

/**
 * This class is the controller of the MVC design, it creates a fully functional
 * connect four game board and makes all decisions regarding the state of the
 * game.
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
	
	private final int CONNECT_FOUR = 4;

	private int rating;

	public ConnectFourGame() {
		setGameStatus(GameStatus.NotOverYet);
		setPlayer(Player.BLUE);
		board = new cfCell[DEFAULT_ROW][DEFAULT_COL];
		initialize();
	}

	private void initialize() {
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				board[row][col] = new cfCell();
			}
		}
	}

	public int selectcfCell(int row, final int col, final Player player) {
		if (getCell(row, col) == null || getCell(row, col).isMarked()) {
			return -1;
		}
		while (getCell(row, col) != null && !getCell(row, col).isMarked()) {
			row++;
		}
		board[row-1][col].setMarked(true);
		board[row-1][col].setPlayer(player);
		if (checkStatus(player)) {
			setGameStatus(GameStatus.Won);
		} else {
			setGameStatus(GameStatus.NotOverYet);
		}
		setPlayer(player.next());
		return 1;
	}

	public boolean checkStatus(final Player player) {
		return (checkHorizontal(player) || checkVertical(player) ||
				checkDiagonal(player));
	}

	private boolean checkHorizontal(final Player player) {
		int count;
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			count = 1;
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				if (getCell(row, col).getPlayer() == player && getCell(row, col + 1) != null &&
						getCell(row, col + 1).getPlayer() == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == getCONNECT_FOUR()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkVertical(final Player player) {
		int count;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			count = 0;
			for (int row = 0; row < getDEFAULT_ROW(); row++) {
				if (getCell(row, col).getPlayer() == player) {
					count++;
				}
				if (count == getCONNECT_FOUR()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkDiagonal(Player player) {
		//TO-DO Diagonal logic
		return false;
	}
	
	public boolean checkTie() {
		int row = 0;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			if (!board[row][col].isMarked()) {
				return false;
			}
		}
		return true;
	}

	public cfCell getCell(final int row, final int col) {
		return (row < 0 || col < 0 || row >= getDEFAULT_ROW() || col >= getDEFAULT_COL()) ? null : board[row][col];
	}

	// probably not going to use this method
	private cfCell[][] getBoard() {
		return board;
	}

	// probably not going to use this method either
	private void setBoard(cfCell[][] board) {
		this.board = board;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	private void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Player getPlayer() {
		return player;
	}

	private void setPlayer(Player player) {
		this.player = player;
	}

	// code for AI
	private int getRating() {
		return rating;
	}

	private void setRating(int rating) {
		this.rating = rating;
	}

	public int getDEFAULT_ROW() {
		return DEFAULT_ROW;
	}

	public int getDEFAULT_COL() {
		return DEFAULT_COL;
	}
	
	public int getCONNECT_FOUR() {
		return CONNECT_FOUR;
	}
}
