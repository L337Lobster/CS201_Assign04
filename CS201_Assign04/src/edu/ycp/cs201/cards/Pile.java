package edu.ycp.cs201.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class to represent a pile of {@link Card}s.
 * Each card has an index, with index 0 being the card
 * at the bottom of the pile.
 * Each pile has an "expose index": all cards whose
 * indices are greater than or equal to the expose index
 * are face-up, and all other cards are face-down.
 */
public class Pile {
	// TODO: add fields

	/**
	 * Constructor.  The pile will be empty initially,
	 * and its expose index will be set to 0.
	 */
	public Pile() {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * @return the expose index
	 */
	public int getExposeIndex() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Set the expose index.
	 * 
	 * @param exposeIndex the expose index to set
	 */
	public void setExposeIndex(int exposeIndex) {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Add a {@link Card} to the pile.  The card added is placed
	 * on top of the cards currently in the pile.
	 * 
	 * @param card the {@link Card} to add
	 */
	public void addCard(Card card) {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * @return the number of @{link Card}s in the pile
	 */
	public int getNumCards() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * @return true if the pile is empty, false otherwise
	 */
	public boolean isEmpty() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Get the {@link Card} whose index is given.
	 * 
	 * @param index the index of the card to get
	 * @return the {@link Card} at the index
	 * @throws NoSuchElementException if the index does not refer to a valid card
	 */
	public Card getCard(int index) {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * Get the {@link Card} on top of the pile.
	 * 
	 * @return the {@link Card} on top of the pile
	 * @throws NoSuchElementException if the pile is empty
	 */
	public Card getTopCard() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * @return the index of the top {@link Card}, or -1 if the pile is empty
	 */
	public int getIndexOfTopCard() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Remove given number of {@link Card}s from the top of the pile.
	 * 
	 * @param numCards number of cards to remove
	 * @return an ArrayList containing the removed cards
	 * @throws IllegalArgumentException if the pile does not have enough {@link Card}s to satisfy the request
	 */
	public ArrayList<Card> removeCards(int numCards) {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Add {@link Card}s to the top of the pile.
	 * 
	 * @param cardsToAdd an ArrayList containing the {@link Card}s to add
	 */
	public void addCards(ArrayList<Card> cardsToAdd) {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Populate the pile by adding 52 {@link Card}s
	 * representing all possible combinations of
	 * {@link Suit} and {@link Rank}.
	 */
	public void populate() {
		throw new UnsupportedOperationException("TODO - implement");
	}

	/**
	 * Shuffle the {@link Card}s in the pile by rearranging
	 * them randomly.
	 */
	public void shuffle() {
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	/**
	 * Remove the top {@link Card} on the pile and return it.
	 * 
	 * @return the removed {@link Card}
	 * @throws NoSuchElementException if the pile is empty
	 */
	public Card drawCard() {
		throw new UnsupportedOperationException("TODO - implement");
	}
}
