package connectfour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	/** For a ConnectFourGUI that holds the buttons for the game. */
	private static ConnectFourGUI board;

	/** A JFrame to add elements to. */
	private static JFrame frame;
	
	/**
	 * Main method that creates a JFrame and adds an instance 
	 * of the ConnectFourGUI to it and sets up the rest of the game.
	 * 
	 */
	public static void main(final String[] args) {
		board = new ConnectFourGUI();
		frame = new JFrame("Connect Four");
		frame.add(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

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
					frame.remove(board);
					board = new ConnectFourGUI();
					clearBoard();
				}

				if (e.getSource() == menuAI) {
					frame.remove(board);
					board = new ConnectFourGUI();
					clearBoard();
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

		menuPlayer = new JMenuItem("Player");
		menuPlayer.addActionListener(new MenuActionListener());
		menuNewGame.add(menuPlayer);

		menu.addSeparator();
		menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new MenuActionListener());
		menu.add(menuExit);

		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}
}
