package connectfour;

/**
 * This class holds the properties of a connect four circle. This is the Model
 * of the MVC design. The properties of this model include: which player controls 
 * the cell, whether or not the cell has been clicked on, and the rating of 
 * the cell that is used to assist the logic of the AI.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public class cfCell {

	/** A Player enum to represent which player selected the cell. */
	private Player player;

	/** Boolean to mark whether or not the cell has been selected. */
	private boolean isMarked;

	/** An integer to represent how good of a move a cell is. */
	private int rating;

	/**
	 * Constructor that instantiates a new cell and sets up the cell with
	 * default properties.
	 * 
	 */
	public cfCell() {
		player = Player.NONE;
		isMarked = false;
		rating = 0;
	}

	/**
	 * Getter method that returns which player owns that particular cell.
	 * 
	 * @return Player The player who currently has that cell selected
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter method that sets the player of a cell.
	 * 
	 * @param player
	 *            The player that is going to be owner of that cell
	 */
	public void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * Getter method that returns whether or not the cell has been selected.
	 * 
	 * @return boolean If the cell has an owner or not
	 */
	public boolean isMarked() {
		return isMarked;
	}

	/**
	 * Setter method that marks the cell as being selected or not.
	 * 
	 * @param isMarked
	 *            Whether or not the cell has been selected
	 */
	public void setMarked(final boolean isMarked) {
		this.isMarked = isMarked;
	}

	/**
	 * Getter method that returns the rating of a cell. The higher rating a cell
	 * has the better the move is.
	 * 
	 * @return int The rating of a cell
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Setter method that sets the rating of a move.
	 * 
	 * @param rating
	 *            The current rating of that cell
	 */
	public void setRating(final int rating) {
		this.rating = rating;
	}
}
