package edu.ycp.cs201.cards;

/**
 * Model class storing information about a Klondike game
 * in progress.  Consists of a number of {@link Pile} objects
 * representing the various piles: main deck, waste,
 * foundation, and tableau piles.  Note that none of the
 * game logic is implemented in this class: all logic
 * is implemented in the {@link KlondikeController} class.
 */
public class KlondikeModel {
	// TODO: add fields
	
	/**
	 * Constructor.  Should create all of the required {@link Pile} objects,
	 * but it should <em>not</em> initialize them.  All piles should start
	 * out as empty.
	 */
	public KlondikeModel() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * @return the {@link Pile} representing the main deck
	 */
	public Pile getMainDeck() {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * Get a reference to one of the tableau piles.
	 * 
	 * @param index index of a tableau pile (in the range 0..6)
	 * @return the tableau {@link Pile}
	 */
	public Pile getTableauPile(int index) {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Get a reference to one of the foundation piles.
	 * 
	 * @param index index of a foundation pile (in the range 0..3)
	 * @return the foundation {@link Pile}
	 */
	public Pile getFoundationPile(int index) {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * @return the {@link Pile} representing the waste pile
	 */
	public Pile getWastePile() {
		throw new UnsupportedOperationException("TODO - implement");
	}
}
