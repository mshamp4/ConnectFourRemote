package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class is the view of the MVC design, it creates and handles all of the
 * GUI components of a connect four game. It interacts with the logic found in
 * the controller, ConnectFourGame, and updates the board based of of decisions
 * made in the controller class.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public class ConnectFourGUI extends JPanel implements ActionListener {

	/** This is used to serialize the ConnectFourGUI class. */
	private static final long serialVersionUID = -2516162043977233740L;

	/**
	 * A 2D array of JButtons that represent each cell 
	 * of a connect four game.
	 */
	private JButton[][] board;

	/** An instance of the ConnectFourGame class. */
	private ConnectFourGame game;

	/** A CfCell that stores information of one connect four cell. */
	private CfCell iCell;

	/** A JPanel to add all of the buttons onto. */
	private JPanel center;

	/** Icon to represent red players moves. */
	private ImageIcon redCircle = new ImageIcon("src/redCircle.png");

	/** Icon that is used when the red player wins the game. */
	private ImageIcon redCircleMini = new 
			ImageIcon("src/redCircleMini.png");

	/** Icon to represent blue players moves. */
	private ImageIcon blueCircle = new ImageIcon("src/blueCircle.png");

	/** Icon that is used when the blue player wins the game. */
	private ImageIcon blueCircleMini = new 
			ImageIcon("src/blueCircleMini.png");

	/**
	 * Default icon that is used at the start of the game to signify a blank
	 * space.
	 */
	private ImageIcon blackCircle = new ImageIcon("src/blackCircle.png");

	/** Icon that is used when both red and blue players tie. */
	private ImageIcon exclamationPoint = new 
			ImageIcon("src/exclamationPoint.png");

	/**
	 * Constructor that instantiates a new game ConnectFourGame, and sets
	 * up the JButtons based off of the default board size found in the
	 * ConnectFourGame class.
	 * 
	 */
	public ConnectFourGUI() {
		game = new ConnectFourGame();
		int row = game.getDEFAULT_ROW();
		int col = game.getDEFAULT_COL();
		board = new JButton[row][col];
		setLayout(new BorderLayout());
		center = new JPanel();
		center.setLayout(new GridLayout(row, col));
		add(center, BorderLayout.CENTER);
		createButtons();
	}
	
	/**
	 * Constructor that instantiates a new ConnectFourGame, and sets
	 * up the JButtons based off of the default board size found in the
	 * ConnectFourGame class. In addition, this also sets the players
	 * color by changing it to the specified color and not just 
	 * defaulting to blue.
	 * 
	 * @param player Which player's color starts first
	 */
	public ConnectFourGUI(final Player player, boolean enabled) {
		game = new ConnectFourGame(player, enabled);
		int row = game.getDEFAULT_ROW();
		int col = game.getDEFAULT_COL();
		board = new JButton[row][col];
		setLayout(new BorderLayout());
		center = new JPanel();
		center.setLayout(new GridLayout(row, col));
		add(center, BorderLayout.CENTER);
		createButtons();
	}

	/**
	 * Helper method that creates each individual default JButton. This
	 * JButton includes no border, and the icon on this button is a
	 * black circle representing that no one is the owner of that cell.
	 * 
	 */
	private void createButtons() {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				board[row][col] = new JButton("");
				board[row][col].setPreferredSize(
						new Dimension(100, 100));
				board[row][col].setBackground(Color.YELLOW);
				board[row][col].setBorder(
						new EmptyBorder(0, 0, 0, 0));
				board[row][col].setIcon(blackCircle);
				board[row][col].addActionListener(this);
				center.add(board[row][col]);
			}
		}
	}
	
	
	/**
	 * Helper method that gets which player made 
	 * the first move of the game.
	 * 
	 * @return Player The player who started the game
	 */
	public Player getInitialPlayer() {
		return game.getStartingPlayer();
	}
	
	public void setAI(boolean status) {
		game.setAiEnabled(status);
	}

	public boolean isAIEnabled() {
		return game.isAiEnabled();
	}
	
	/**
	 * Helper method that updates the board every time the user clicks on a
	 * JButton.
	 * 
	 */
	private void displayBoard() {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				iCell = game.getCell(game.getBoard(), row, col);
				if (iCell.isMarked() && iCell.getPlayer()
						== Player.PLAYER1) {
					board[row][col].setIcon(blueCircle);
				}
				if (iCell.isMarked() && iCell.getPlayer()
						== Player.PLAYER2) {
					board[row][col].setIcon(redCircle);
				}
			}
		}
	}
	
	@Override
	/**
	 * Helper method that controls what happens when the user 
	 * clicks on a cell. Whichever cell the user selected is 
	 * sent to the ConnectFourGame class to decide the logic
	 * based off of that move. It also updates the board
	 * and checks to see if there is a winner or a tie.
	 * 
	 * @param e
	 *            The event that is triggered when the user clicks on a
	 *            JButton
	 * 
	 */
	public void actionPerformed(final ActionEvent e) {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				if (e.getSource() == board[row][col]
						&& game.getGameStatus()
						== GameStatus.NotOverYet) {
					game.selectMove(row, col, 
							game.getPlayer());
					
					if (game.isAiEnabled() && game.getPlayer() == Player.PLAYER2) {
						if (game.getAiRandomMove()) {
							Move move = game.generateRandomMove(game.getBoard(), game.getPlayer());
							if (move != null) {
								game.selectMove(move.getRow(), move.getCol(), game.getPlayer());
							}
						} else {
							Move move = game.miniMax(game.getCurrentState(game.getBoard()), game.getPlayer());
							if (move != null) {
								game.selectMove(move.getRow(), move.getCol(), game.getPlayer());
							}
						}
					}
				}
			}
		}

		displayBoard();

		if (game.getGameStatus() == GameStatus.Won && game.getPlayer()
				== Player.PLAYER1) {
			JOptionPane.showMessageDialog(null, "Red Wins!", "Red",
					JOptionPane.INFORMATION_MESSAGE,
					redCircleMini);
		}
		if (game.getGameStatus() == GameStatus.Won && game.getPlayer()
				== Player.PLAYER2) {
			JOptionPane.showMessageDialog(null, "Blue Wins!",
					"Blue", JOptionPane.INFORMATION_MESSAGE,
					blueCircleMini);
		}
		if (game.getGameStatus() == GameStatus.NotOverYet
				&& game.checkTie(game.getBoard())) {
			JOptionPane.showMessageDialog(null, "It's a tie!",
					"Tie", JOptionPane.INFORMATION_MESSAGE,
					exclamationPoint);
		}
	}
}
