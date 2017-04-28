package connectfour;

import java.util.ArrayList;
import java.util.Collections;

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
	
	private boolean firstMove;
	
	private boolean aiEnabled;
	
	private int aiRandomMove;

	// CHECKSTYLE:OFF
	/** Default row of a connect four board. */
	private final int DEFAULT_ROW = 6;

	/** Default column of a connect four board. */
	private final int DEFAULT_COL = 7;

	/** Winning condition is to get 4 in a row. */
	private final int CONNECT_FOUR = 4;
	// CHECKSTYLE:ON
	
	/**
	 * Constructor that instantiates a new game by setting all of the
	 * default values and by setting the first player's turn to blue.
	 * 
	 */
	public ConnectFourGame() {
		setGameStatus(GameStatus.NotOverYet);
		setPlayer(Player.PLAYER1);
		setStartingPlayer(Player.PLAYER1);
		setAiEnabled(true);
		board = new CfCell[DEFAULT_ROW][DEFAULT_COL];
		initialize();
		setAiRandomMove(0);
		setFirstMove(true);
	}
	
	/**
	 * Constructor that instantiates a new game and works similarly 
	 * to the default constructor however it sets the players
	 * color to a value provided by the parameter.
	 * 
	 * 
	 * @param player The color that the first player is going to be
	 */
	public ConnectFourGame(final Player player, boolean enabled) {
		setGameStatus(GameStatus.NotOverYet);
		setPlayer(player);
		setStartingPlayer(player);
		setAiEnabled(enabled);
		board = new CfCell[DEFAULT_ROW][DEFAULT_COL];
		initialize();
		setAiRandomMove(0);
		setFirstMove(true);
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
	public int selectMove(int row, final int col, final Player player) {
		if (getCell(getBoard(), row, col) == null || getCell(getBoard(), row, col).isMarked()) {
			return -1;
		}
		while (getCell(getBoard(), row, col) != null && !getCell(getBoard(), row, col).
				isMarked()) {
			row++;
		}
		board[row - 1][col].setMarked(true);
		board[row - 1][col].setPlayer(player);
		if (checkStatus(board, row - 1, col, player)) {
			setGameStatus(GameStatus.Won);
		} else {
			setGameStatus(GameStatus.NotOverYet);
		}
		setPlayer(player.next());
		return 1;
	}

	private void makeMove(CfCell[][] board, final int row, final int col, Player player) {
		if (board[row][col] == null) {
			throw new NullPointerException();
		}
		board[row][col].setMarked(true);
		board[row][col].setPlayer(player);
	}
	
	private void undoMove(CfCell[][] board, final int row, final int col) {
		if (board[row][col] == null) {
			throw new NullPointerException();
		}
		board[row][col].setMarked(false);
		board[row][col].setPlayer(Player.NONE);
	}
	
	public Move miniMax(CfCell[][] board, Player player) {
		ArrayList<Move> availableMoves = availableMoves(board, player);
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		Move bestMove = null;
		
		if (availableMoves == null || availableMoves.size() == 0) {
			return bestMove;
		}
		
		if (player == Player.PLAYER1) {
			bestMove = min(availableMoves);
			if (bestMove.getRating() == -10) {
				bestMoves.add(bestMove);
			}
		} 
		
		if (player == Player.PLAYER2) {
			bestMove = max(availableMoves);
			if (bestMove.getRating() == 10) {
				bestMoves.add(bestMove);
			}
		}
	
		makeMove(board, bestMove.getRow(), bestMove.getCol(), player);
		
		if (bestMove.getRating() == 0 && checkTie(board)) {
			bestMoves.add(bestMove);
		}
		
		if (bestMove.getRating() == 0 && !checkTie(board)) {
//			bestMoves.add(miniMax(board, player.next()));
			bestMoves.add(miniMax(board, player.next()));
		}
//		undoMove(board, bestMove.getRow(), bestMove.getCol());
		return max(bestMoves);
	}
	
	public Move max(ArrayList<Move> availableMoves) {
		Move maxMove = availableMoves.get(0);
		int rating = availableMoves.get(0).getRating();
		for (Move move : availableMoves) {
			if (move.getRating() >= rating) {
				rating = move.getRating();
				maxMove = move;
			}
		}
		return maxMove;
	}
	
	public Move min(ArrayList<Move> availableMoves) {
		Move minMove = availableMoves.get(0);
		int rating = availableMoves.get(0).getRating();
		for (Move move : availableMoves) {
			if (move.getRating() <= rating) {
				rating = move.getRating();
				minMove = move;
			}
		}
		return minMove;
	}
	
	public Move generateRandomMove(CfCell board[][], Player player) {
		if (getCell(board, (getDEFAULT_ROW() - 1), (getDEFAULT_COL() / 2)) != null && !board[getDEFAULT_ROW() - 1][getDEFAULT_COL() / 2].isMarked()) {
			Move centerMove = new Move (getDEFAULT_ROW() - 1, (getDEFAULT_COL() / 2), player);
			setAiRandomMove(1);
			return centerMove;
		}
		
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		if (firstMove) {
			availableMoves = generateBlock(board, player);
			setFirstMove(false);
		} else {
			availableMoves = availableMoves(board, player); 
			availableMoves.remove(availableMoves.size() - 1);
			availableMoves.remove(0);
		}
		
		if (availableMoves.size() == 0) {
			return null;
		}
		
		setAiRandomMove(1);
		return randomMove(availableMoves);
	}
	
	private Move randomMove(ArrayList<Move> moves) {
		Collections.shuffle(moves);
		return moves.get(0);
	}
	
	private ArrayList<Move> generateBlock(CfCell board[][], Player player) {
		ArrayList<Move> blockMoves = new ArrayList<Move>();
		if (!checkTie(board) && getGameStatus() != GameStatus.Won) {
			for (int row = getDEFAULT_ROW() - 1; row >= getDEFAULT_ROW() - 1; row--) {
				for (int col = 0; col < getDEFAULT_COL(); col++) {
					if (board[row][col].isMarked() && board[row][col].getPlayer() == player.next()) {
						if (getCell(board, row, col - 1) != null && !board[row][col - 1].isMarked()) {
							Move left = new Move(row, col - 1, player);
							blockMoves.add(left);
						}
						if (getCell(board, row, col + 1) != null && !board[row][col + 1].isMarked()) {
							Move right = new Move(row, col + 1, player);
							blockMoves.add(right);
						}
						if (getCell(board, row - 1, col) != null && !board[row - 1][col].isMarked()) {
							Move up = new Move(row - 1, col, player);
							blockMoves.add(up);
						}
					}
				}
			}
		}
		return blockMoves;
	}
	
	private ArrayList<Move> availableMoves(CfCell[][] board, Player player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		if (!checkTie(board) && getGameStatus() != GameStatus.Won) {
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				for (int row = getDEFAULT_ROW() - 1; row >= 0; row--) {
					if (!board[row][col].isMarked() && board[row][col].getPlayer() == Player.NONE) {
						Move newMove = new Move(row, col, player);
						moves.add(newMove);
						row = -1;
						rateMove(board, newMove, player);
					}
				}
			}
		}
		return moves;
	}
	
	private void rateMove(CfCell[][] currentState, Move move, Player player) {
		int score = 0;
		makeMove(currentState, move.getRow(), move.getCol(), player);
		if (checkStatus(currentState, move.getRow(), move.getCol(), player)) {
			if (player == Player.PLAYER1) {
				score = -10;
			} else {
				score = 10;
			}
		}
		undoMove(currentState, move.getRow(), move.getCol());
		move.setRating(score);
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
	private boolean checkStatus(CfCell[][] board, final int row, final int col, final 
			Player player) {
		return (checkHorizontal(board, row, player)
				|| checkVertical(board, col, player)
				|| checkDiagonal(board, row, col, player));
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
	private boolean checkHorizontal(CfCell[][] board, final int row, final Player player) {
		int count = 0;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			if (board[row][col].getPlayer() == player) {
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
	private boolean checkVertical(CfCell[][] board, final int col, final Player player) {
		int count = 0;
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			if (board[row][col].getPlayer() == player) {
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
	private boolean checkDiagonal(CfCell[][] board, final int row, final int col,
			final Player player) {
		return (checkForwardDiagonal(board, row, col, player)
				|| checkBackwardDiagonal(board, row, col, player));
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
	private boolean checkForwardDiagonal(CfCell[][] board, final int row, final int col,
			final Player player) {
		int forwardCount = 0;
		int rUpValue = row;
		int cUpValue = col;
		int rDownValue = row + 1;
		int cDownValue = col - 1;
		while (getCell(board, rUpValue, cUpValue) != null
				&& getCell(board, rUpValue, cUpValue).getPlayer() 
				== player) {
			forwardCount++;
			rUpValue--;
			cUpValue++;
			if (forwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		while (getCell(board, rDownValue, cDownValue) != null
				&& getCell(board, rDownValue, cDownValue).getPlayer() 
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
	private boolean checkBackwardDiagonal(CfCell[][] board, final int row, final int col,
			final Player player) {
		int backwardCount = 0;
		int rUpValue = row;
		int cUpValue = col;
		int rDownValue = row + 1;
		int cDownValue = col + 1;
		while (getCell(board, rUpValue, cUpValue) != null
				&& getCell(board, rUpValue, cUpValue).getPlayer() 
				== player) {
			backwardCount++;
			rUpValue--;
			cUpValue--;
			if (backwardCount == getCONNECT_FOUR()) {
				return true;
			}
		}
		while (getCell(board, rDownValue, cDownValue) != null
				&& getCell(board, rDownValue, cDownValue).getPlayer()
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
	public boolean checkTie(final CfCell[][] board) {
		int row = 0;
		for (int col = 0; col < getDEFAULT_COL(); col++) {
			if (getCell(board, row, col) != null && !board[row][col].isMarked()) {
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
	public CfCell getCell(CfCell board[][], final int row, final int col) {
		return (row < 0 || col < 0 || row >= getDEFAULT_ROW()
				|| col >= getDEFAULT_COL())
				? null : board[row][col];
	}
	
	public CfCell[][] getBoard() {
		return board;
	}
		
	public CfCell[][] getCurrentState(CfCell[][] currentState) {
		CfCell[][] newState = new CfCell[DEFAULT_ROW][DEFAULT_COL];
		for (int row = 0; row < getDEFAULT_ROW(); row++) {
			for (int col = 0; col < getDEFAULT_COL(); col++) {
				if (getCell(getBoard(), row, col) != null) {
					CfCell temp = getCell(currentState, row, col);
					if (temp.isMarked()) {
						newState[row][col] = new CfCell();
						newState[row][col].setPlayer(temp.getPlayer());
						newState[row][col].setMarked(true);
					} else {
						newState[row][col] = new CfCell();
					}
				}
			}
		}
		return newState;
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
	
	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	
	public boolean isAiEnabled() {
		return aiEnabled;
	}

	public void setAiEnabled(boolean aiEnabled) {
		this.aiEnabled = aiEnabled;
	}
	
	public boolean getAiRandomMove() {
		return (aiRandomMove < 2);
	}

	private void setAiRandomMove(int aiRandomMove) {
		this.aiRandomMove += aiRandomMove;
	}

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
