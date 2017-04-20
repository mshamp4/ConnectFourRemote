package connectfour;

/**
 * This enum is used to easily track the status of the
 * connect four game.
 * 
 * @author Matthew Shampine
 * @version 1.0
 */
public enum GameStatus {
	
	/**
	 * The current status if the user lost.
	 * 
	 */
	Lost, 
	
	/**
	 * The current status if the user won.
	 * 
	 */
	Won,
	
	/**
	 * The current status if the game is not over yet.
	 * 
	 */
	NotOverYet
}