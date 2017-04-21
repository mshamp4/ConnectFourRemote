package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	private ImageIcon blueCircle = new ImageIcon("src/blueCircle.png");
	
	private ImageIcon blackCircle = new ImageIcon("src/blackCircle.png");

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
				board[row][col].setPreferredSize(
						new Dimension(100, 100));
				board[row][col].setBackground(Color.YELLOW);
				board[row][col].setBorder(new EmptyBorder(0, 0, 0, 0));
				board[row][col].setIcon(blackCircle);
				board[row][col].addActionListener(this);
				center.add(board[row][col]);
			}
		}
	}
	
	private void disableButtons() {
		for (int row = 0; row < game.getDEFAULT_ROW(); row++) {
			for (int col = 0; col < game.getDEFAULT_COL(); col++) {
				board[row][col].setEnabled(false);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
