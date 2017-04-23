package connectfour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class creates a JFrame to add the ConnectFourGUI to it and provides
 * different options as well like exiting the game, and the option of creating a
 * new game with a player or AI.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public class ConnectFour {

	/** Creates a menu at the top of the GUI. */
	private static JMenuBar menuBar;

	/** For a tab 'menu' in menuBar. */
	private static JMenu menu;

	/** An item in the menu to exit the game. */
	private static JMenuItem menuExit;

	/** A menu on the main menu to select the opponent to a player or AI. */
	private static JMenu menuNewGame;

	/** Switch the opponent to AI. */
	private static JMenuItem menuAI;

	/** Switch the opponent to player. */
	private static JMenuItem menuPlayer;

	/** Changes the color of the player. */
	private static JMenuItem menuSwitchColors;

	/** For a ConnectFourGUI that holds the buttons for the game. */
	private static ConnectFourGUI board;

	/** A JFrame to add elements to. */
	private static JFrame frame;

	/** A warning icon that is used to switch colors. */
	private static ImageIcon warningIcon = new 
			ImageIcon("src/warningIcon.png");

	/**
	 * Main method that creates a JFrame and adds an instance of the
	 * ConnectFourGUI to it and sets up the rest of the game.
	 * 
	 * @param args
	 *            Arguments that are passed through the main method these
	 *            aren't used
	 */
	public static void main(final String[] args) {
		board = new ConnectFourGUI();
		frame = new JFrame("Connect Four");
		frame.add(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);

		/**
		 * This class is used for the different menu options.
		 * 
		 * @author Matthew Shampine
		 */
		class MenuActionListener implements ActionListener {

			@Override
			/**
			 * Performs an action based on a menu selection.
			 * 
			 */
			public void actionPerformed(final ActionEvent e) {
				if (e.getSource() == menuPlayer) {
					Player player = selectPlayer();
					frame.remove(board);
					board = new ConnectFourGUI(player);
					clearBoard();
				}
				if (e.getSource() == menuAI) {
					Player player = selectPlayer();
					frame.remove(board);
					board = new ConnectFourGUI(player);
					clearBoard();
				}
				if (e.getSource() == menuSwitchColors) {
					if (alertMessage() == 0) {
						Player player = 
								selectPlayer().next();
						frame.remove(board);
						board = new 
								ConnectFourGUI(player);
						clearBoard();
					}
				}
				if (e.getSource() == menuExit) {
					System.exit(0);
				}
			}

			/**
			 * This method adds the new board to the frame.
			 * 
			 */
			private void clearBoard() {
				frame.add(board);
				frame.pack();
				frame.setLocationRelativeTo(null);
			}

			
			/**
			 * This method returns the color of which player
			 * started first.
			 * 
			 * @return Player Returns the color of the starting
			 * 		   player
			 */
			private Player selectPlayer() {
				if (board.getInitialPlayer() == Player.PLAYER1) {
					return Player.PLAYER1;
				}
				return Player.PLAYER2;
			}

			/**
			 * This method makes the user aware that a new game
			 * will be created if the colors are switched.
			 * 
			 * @return int If the user wants to change the game
			 */
			private int alertMessage() {
				int reply = JOptionPane.
						showConfirmDialog(null,
						"Warning! A new game will be "
						+ "created\nif players switch "
						+ "colors.", "Change Colors",
						JOptionPane.
						YES_NO_OPTION, JOptionPane.
						INFORMATION_MESSAGE, warningIcon);

				if (reply == JOptionPane.YES_OPTION) {
					return 0;
				}
				return 1;
			}
		}

		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		menuBar.add(menu);

		menuNewGame = new JMenu("New Game");
		menuNewGame.addActionListener(new MenuActionListener());
		menu.add(menuNewGame);

		menuAI = new JMenuItem("AI");
		menuAI.addActionListener(new MenuActionListener());
		menuNewGame.add(menuAI);
		menuAI.setEnabled(false);

		menuPlayer = new JMenuItem("Player");
		menuPlayer.addActionListener(new MenuActionListener());
		menuNewGame.add(menuPlayer);

		menu.addSeparator();
		menuSwitchColors = new JMenuItem("Switch Colors");
		menuSwitchColors.addActionListener(new MenuActionListener());
		menu.add(menuSwitchColors);

		menu.addSeparator();
		menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new MenuActionListener());
		menu.add(menuExit);

		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}
}
