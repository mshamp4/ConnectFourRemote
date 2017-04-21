package connectfour;

/**
 * This enum is used to easily track which player owns what cell and also to
 * keep track of whose turn it is.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public enum Player {
	
	/**
	 * Blue player.
	 * 
	 */
	BLUE,
	
	/**
	 * Red player.
	 * 
	 */
	RED, 
	
	/**
	 * There is no owner of a particular cell.
	 * 
	 */
	NONE;

	/**
	 * This method changes the turn of the player depending on whose turn it
	 * currently is.
	 * 
	 * @return Player the player whose next turn it is
	 */
	public Player next() {
		return this == BLUE ? RED : BLUE;
	}
}