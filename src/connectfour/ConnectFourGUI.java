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

public class ConnectFourGUI extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2516162043977233740L;

	private JButton[][] board;

	private ConnectFourGame game;

	private cfCell iCell;

	private JPanel center;

	private ImageIcon redCircle = new ImageIcon("src/redCircle.png");
	
	private ImageIcon redCircleMini = new ImageIcon("src/redCircleMini.png");

	private ImageIcon blueCircle = new ImageIcon("src/blueCircle.png");
	
	private ImageIcon blueCircleMini = new ImageIcon("src/blueCircleMini.png");

	private ImageIcon blackCircle = new ImageIcon("src/blackCircle.png");
	
	private ImageIcon exclamationPoint = new ImageIcon("src/exclamationPoint.png");
	
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

	private void createButtons() {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				board[row][col] = new JButton("");
				board[row][col].setPreferredSize(new Dimension(100, 100));
				board[row][col].setBackground(Color.YELLOW);
				board[row][col].setBorder(new EmptyBorder(0, 0, 0, 0));
				board[row][col].setIcon(blackCircle);
				board[row][col].addActionListener(this);
				center.add(board[row][col]);
			}
		}
	}

	private void displayBoard() {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				iCell = game.getCell(row, col);
				if (iCell.isMarked() && iCell.getPlayer() == Player.BLUE) {
					board[row][col].setIcon(blueCircle);
				}
				if (iCell.isMarked() && iCell.getPlayer() == Player.RED) {
					board[row][col].setIcon(redCircle);
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				if (e.getSource() == board[row][col] && game.getGameStatus() == GameStatus.NotOverYet) {
					game.selectcfCell(row, col, game.getPlayer());
					}
				}
			}

		displayBoard();
		
		if (game.getGameStatus() == GameStatus.Won && game.getPlayer() == Player.BLUE) {
			JOptionPane.showMessageDialog(null, "Red Wins!", "Red", 
					JOptionPane.INFORMATION_MESSAGE, redCircleMini);
		}
		if (game.getGameStatus() == GameStatus.Won && game.getPlayer() == Player.RED) {
			JOptionPane.showMessageDialog(null, "Blue Wins!", "Blue", 
					JOptionPane.INFORMATION_MESSAGE, blueCircleMini);
		}
		if (game.getGameStatus() == GameStatus.NotOverYet && game.checkTie()) {
			JOptionPane.showMessageDialog(null, "It's a tie!", "Tie", 
					JOptionPane.INFORMATION_MESSAGE, exclamationPoint);
		}
	}
}
