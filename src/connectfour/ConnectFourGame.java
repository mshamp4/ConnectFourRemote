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

	/** A 2D array full of connect four cells. */
	private CfCell[][] board;

	/** Current status of the game. */
	private GameStatus gameStatus;

	/** Used to keep track of the current turn of the game. */
	private Player player;
	
	/** Used to keep track of who starts first. */
	private Player startingPlayer;

	// CHECKSTYLE:OFF
	/** Default row of a connect four board. */
	private final int DEFAULT_ROW = 6;

	/** Default column of a connect four board. */
	private final int DEFAULT_COL = 7;

	/** Winning condition is to get 4 in a row. */
	private final int CONNECT_FOUR = 4;
	// CHECKSTYLE:ON
	
//	/** An integer used by the AI to represent how good a move is. */
//	private int rating;

	/**
	 * Constructor that instantiates a new game by setting all of the
	 * default values and by setting the first player's turn to blue.
	 * 
	 */
	public ConnectFourGame() {
		setGameStatus(GameStatus.NotOverYet);
		setPlayer(Player.PLAYER1);
		setStartingPlayer(Player.PLAYER1);
		board = new CfCell[DEFAULT_ROW][DEFAULT_COL];
		initialize();
	}
	
	/**
	 * Constructor that instantiates a new game and works similarly 
	 * to the default constructor however it sets the players
	 * color to a value provided by the parameter.
	 * 
	 * 
	 * @param player The color that the first player is going to be
	 */
	public ConnectFourGame(final Player player) {
		setGameStatus(GameStatus.NotOverYet);
		setPlayer(player);
		setStartingPlayer(player);
		board = new CfCell[DEFAULT_ROW][DEFAULT_COL];
		initialize();
	}

	/**
	 * Helper method that creates individual default cells for the
	 * ConnectFour game board.
	 * 
	 */
	private void initialize() {
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				board[row][col] = new CfCell();
			}
		}
	}

	/**
	 * Helper method that controls the logic when a user clicks on a connect
	 * four Cell. This method automatically selects the lowest row in a
	 * column of cells and checks the game status to see if there is a
	 * winner. In addition to this it also changes the turn of the player
	 * on a successful move and also checks for invalid moves.
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return int Whether or not the move that is being made is valid or
	 * 			   not
	 */
	public int selectCfCell(int row, final int col, final Player player) {
		if (getCell(row, col) == null || getCell(row, col).isMarked()) {
			return -1;
		}
		while (getCell(row, col) != null && !getCell(row, col).
				isMarked()) {
			row++;
		}
		board[row - 1][col].setMarked(true);
		board[row - 1][col].setPlayer(player);
		if (checkStatus(row - 1, col, player)) {
			setGameStatus(GameStatus.Won);
		} else {
			setGameStatus(GameStatus.NotOverYet);
		}
		setPlayer(player.next());
		return 1;
	}

	/**
	 * Helper method that checks the game board every time a new cell is
	 * added. This method checks for all of the win conditions including
	 * horizontal, vertical and both directions of diagonals.
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	public boolean checkStatus(final int row, final int col, final 
			Player player) {
		return (checkHorizontal(row, player)
				|| checkVertical(col, player)
				|| checkDiagonal(row, col, player));
	}

	/**
	 * Helper method that checks how many consecutive cells of a single
	 * player there are horizontally. The winning condition is met if
	 * there is enough consecutive cells to equal getCONNECT_FOUR().
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	private boolean checkHorizontal(final int row, final Player player) {
		int count = 0;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			if (getCell(row, col).getPlayer() == player) {
				count++;
			} else {
				count = 0;
			}
			if (count == getCONNECT_FOUR()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method that checks how many consecutive cells of a single
	 * player there are vertically. The winning condition is met if
	 * there is enough consecutive cells to equal getCONNECT_FOUR().
	 * 
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	private boolean checkVertical(final int col, final Player player) {
		int count = 0;
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			if (getCell(row, col).getPlayer() == player) {
				count++;
			} else {
				count = 0;
			}
			if (count == getCONNECT_FOUR()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method that checks the diagonals of a connect four game board.
	 * This method could probably be optimized a little because it contains
	 * four separate loops to find both the forward and backward diagonals
	 * of a game board.
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	private boolean checkDiagonal(final int row, final int col,
			final Player player) {
		return (checkForwardDiagonal(row, col, player)
				|| checkBackwardDiagonal(row, col, player));
	}

	/**
	 * Helper method that contains the logic to find how many consecutive
	 * cells of a single player there are diagonally forward. The winning
	 * condition is met if there is enough consecutive cells to equal 
	 * getCONNECT_FOUR().
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	private boolean checkForwardDiagonal(final int row, final int col,
			final Player player) {
		int forwardCount = 0;
		int rUpValue = row;
		int cUpValue = col;
		int rDownValue = row + 1;
		int cDownValue = col - 1;
		while (getCell(rUpValue, cUpValue) != null
				&& getCell(rUpValue, cUpValue).getPlayer() 
				== player) {
			forwardCount++;
			rUpValue--;
			cUpValue++;
			if (forwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		while (getCell(rDownValue, cDownValue) != null
				&& getCell(rDownValue, cDownValue).getPlayer() 
				== player) {
			forwardCount++;
			rDownValue++;
			cDownValue--;
			if (forwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method that contains the logic to find how many consecutive
	 * cells of a single player there are diagonally backward. The
	 * winning condition is met if there is enough consecutive cells to
	 * equal getCONNECT_FOUR().
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @param player
	 *            Who the current player is
	 * @return boolean Checks to see if there is a winner or not
	 */
	private boolean checkBackwardDiagonal(final int row, final int col,
			final Player player) {
		int backwardCount = 0;
		int rUpValue = row;
		int cUpValue = col;
		int rDownValue = row + 1;
		int cDownValue = col + 1;
		while (getCell(rUpValue, cUpValue) != null
				&& getCell(rUpValue, cUpValue).getPlayer() 
				== player) {
			backwardCount++;
			rUpValue--;
			cUpValue--;
			if (backwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		while (getCell(rDownValue, cDownValue) != null
				&& getCell(rDownValue, cDownValue).getPlayer()
				== player) {
			backwardCount++;
			rDownValue++;
			cDownValue++;
			if (backwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method that checks to see if the top row of the game board has
	 * been filled completely and that there are no more available moves to
	 * make.
	 * 
	 * @return boolean Checks to see if there is a tie or not
	 */
	public boolean checkTie() {
		int row = 0;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			if (!board[row][col].isMarked()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Getter method that gets a cell given a row and a column coordinate.
	 * 
	 * @param row
	 *            The row of the cell that was selected
	 * @param col
	 *            The column of the cell that was selected
	 * @return CfCell Returns the CfCell if it is valid and does not exceed
	 * 		   the bounds of the game board.
	 */
	public CfCell getCell(final int row, final int col) {
		return (row < 0 || col < 0 || row >= getDEFAULT_ROW()
				|| col >= getDEFAULT_COL())
				? null : board[row][col];
	}

	/**
	 * Getter method that returns the current status of the game.
	 * 
	 * @return GameStatus The current playing state of the game
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Setter method that sets the current state of the game.
	 * 
	 * @param gameStatus
	 *            The current playing state of the game
	 */
	private void setGameStatus(final GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * Getter method that returns the current player of the game.
	 * 
	 * @return Player Which players turn it is
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter method that sets the current player of the game.
	 * 
	 * @param player
	 *            Which players turn it is
	 */
	private void setPlayer(final Player player) {
		this.player = player;
	}
	
	/**
	 * Getter method that returns what player made the 
	 * first move.
	 * 
	 * @return Player Which player started first
	 */
	public Player getStartingPlayer() {
		return startingPlayer;
	}

	/**
	 * Setter method that sets which player makes the
	 * first move of the game.
	 * 
	 * @param startingPlayer
	 *            Which players turn it is
	 */
	public void setStartingPlayer(final Player startingPlayer) {
		this.startingPlayer = startingPlayer;
	}

//	/**
//	 * Getter method that gets the rating of a particular game cell.
//	 * 
//	 * @return int The rating of the current cell being requested
//	 */
//	private int getRating() {
//		return rating;
//	}
//
//	/**
//	 * Setter method that sets the rating of a particular game cell.
//	 * 
//	 * @param rating
//	 *            The rating of the cell
//	 */
//	private void setRating(final int rating) {
//		this.rating = rating;
//	}

	// CHECKSTYLE:OFF
	/**
	 * Getter method that gets the default row of the game board.
	 * 
	 * @return int The default size of the row
	 */
	public int getDEFAULT_ROW() {
		return DEFAULT_ROW;
	}

	/**
	 * Getter method that gets the default column of the game board.
	 * 
	 * @return int The default size of the column
	 */
	public int getDEFAULT_COL() {
		return DEFAULT_COL;
	}

	/**
	 * Getter method that gets how many consecutive cells there must be in
	 * order for the winning condition to be met. In standard connect four
	 * this value is set to four consecutive cells of the same player.
	 * 
	 * @return int The default number of consecutive cells of the same
	 * 			   player to win the game
	 */
	public int getCONNECT_FOUR() {
		return CONNECT_FOUR;
	}
	// CHECKSTYLE:ON
}
