package edu.ycp.cs201.cards;

import java.util.ArrayList;

/**
 * Model class storing information about a Klondike game
 * in progress.  Consists of a number of {@link Pile} objects
 * representing the various piles: main deck, waste,
 * foundation, and tableau piles.  Note that none of the
 * game logic is implemented in this class: all logic
 * is implemented in the {@link KlondikeController} class.
 */
public class KlondikeModel {
	//ArrayList of ArrayLists of type Card.
	//tableaus will be the tableaus and foundation will be the foundations
	private ArrayList<Pile> tableaus, foundation; 
	//mainDeck is the main deck and waste is the waste pile
	private Pile mainDeck, waste;
	/**
	 * Constructor.  Should create all of the required {@link Pile} objects,
	 * but it should <em>not</em> initialize them.  All piles should start
	 * out as empty.
	 */
	public KlondikeModel() {
		tableaus = new ArrayList<Pile>();
		foundation = new ArrayList<Pile>();
		mainDeck = new Pile();
		waste = new Pile();
		for(int i = 0; i < 7; i++)
		{
			tableaus.add(new Pile());
			if(i < 4)
			{
				foundation.add(new Pile());
			}
		}
	}
	
	/**
	 * @return the {@link Pile} representing the main deck
	 */
	public Pile getMainDeck() {
		return mainDeck;
	}

	/**
	 * Get a reference to one of the tableau piles.
	 * 
	 * @param index index of a tableau pile (in the range 0..6)
	 * @return the tableau {@link Pile}
	 */
	public Pile getTableauPile(int index) {
		if(index > 6)
		{
			throw new IndexOutOfBoundsException();
		}
		return tableaus.get(index);
	}
	
	/**
	 * Get a reference to one of the foundation piles.
	 * 
	 * @param index index of a foundation pile (in the range 0..3)
	 * @return the foundation {@link Pile}
	 */
	public Pile getFoundationPile(int index) {
		if(index > 3)
		{
			throw new IndexOutOfBoundsException();
		}
		return foundation.get(index);
	}

	/**
	 * @return the {@link Pile} representing the waste pile
	 */
	public Pile getWastePile() {
		return waste;
	}
}
