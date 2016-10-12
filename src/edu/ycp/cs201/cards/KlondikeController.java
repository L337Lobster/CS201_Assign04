package edu.ycp.cs201.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The controller class implements all of the logic required to
 * play a game of Klondike.  All of the data is represented
 * in a {@link KlondikeModel} object, and each controller method
 * takes a reference to the model object as a parameter. 
 */
public class KlondikeController {
	/**
	 * Initialize the model object.
	 * Should populate and shuffle the main deck, and then
	 * deal cards from the main deck onto the seven tableau piles.
	 * It should set the expose index of each tableau pile
	 * so that only the top card is exposed.
	 * It should set the expose index of the main deck so that
	 * only the top card is exposed.
	 * It should set the expose index of the waste pile so that
	 * no cards can ever be exposed (i.e., set it to a high value,
	 * such that even if it contains all 52 cards, none of them
	 * would be exposed).
	 * It should set the expose index of each foundation pile to 0.
	 * 
	 * @param model the {@link KlondikeModel} object to initialize
	 */
	public void initModel(KlondikeModel model) {
		//init model, populate it with a full 52 cards, then shuffle it
		model.getMainDeck().populate();
		model.getMainDeck().shuffle();
		//deal to the tableaus and set the expose index to just the top card
		for(int i = 0; i < model.getTableaus().size(); i++)
		{
			model.getTableauPile(i).addCards(model.getMainDeck().removeCards(i+1));
			model.getTableauPile(i).setExposeIndex(model.getTableauPile(i).getIndexOfTopCard());
		}
		//set expose index of main deck to just the top card
		model.getMainDeck().setExposeIndex(model.getMainDeck().getIndexOfTopCard());
		//set expose index of waste deck to 53 so that no cards will ever be exposed
		model.getWastePile().setExposeIndex(53);
		//set expose index of foundation piles to 0
		for(int i = 0; i < model.getFoundations().size(); i++)
		{
			model.getFoundationPile(i).setExposeIndex(0);
		}
	}

	/**
	 * <p>Attempt to create a {@link Selection} that represents one
	 * or more {@link Card}s to be moved from a pile
	 * indicated by a {@link Location}.  The {@link Location} specifies
	 * which pile to move cards from, and which card or cards
	 * within the pile should be moved.  Note that this method
	 * must check to see whether the proposed move is legal:
	 * it should return <code>null</code> if it is not legal
	 * to move the card or cards indicated by the {@link Location}.</p>
	 * 
	 * <p>If the {@link Location} has {@link LocationType#MAIN_DECK} as
	 * its location type, then the main deck must not be empty,
	 * the selected card must be the top card on the main deck.
	 * </p>
	 * 
	 * <p>If the {@link Location} has {@link LocationType#TABLEAU_PILE}
	 * as its location type, then the {@link Location}'s card index
	 * must refer to a valid card, and the card index must be
	 * greater than or equal to the tableau pile's expose index.</p>
	 * 
	 * <p>It is not legal to move cards from the waste pile or from
	 * a foundation pile, so if the {@link Location}'s location type
	 * is {@link LocationType#WASTE_PILE} or {@link LocationType#FOUNDATION_PILE},
	 * then the method should return null.</p>
	 * 
	 * <p>If the move is legal, the indicated cards should be removed
	 * from the source pile (as indicated by the {@link Location}),
	 * and transfered into the {@link Selection} object returned from
	 * the method.
	 * 
	 * @param model   the {@link KlondikeModel}
	 * @param location the {@link Location} specifying which card or cards to move
	 * @return a {@link Selection} containing the cards to move and the
	 *         {@link Location}, or null if the {@link Location} does not
	 *         indicate a legal location from which cards can be moved
	 */
	public Selection select(KlondikeModel model, Location location) {
		Selection select = null;
		switch(location.getLocationType())
		{
		case FOUNDATION_PILE:
			break;
		case MAIN_DECK:
			if(!model.getMainDeck().isEmpty() && location.getCardIndex() == model.getMainDeck().getIndexOfTopCard())
			{
				select = new Selection(location, model.getMainDeck().removeCards(1));
			}
			break;
		case TABLEAU_PILE:
			//checks to make sure the card in the specified tableau pile is not null and is exposed
			if((model.getTableauPile(location.getPileIndex()).getCard(location.getCardIndex()) != null) && (location.getCardIndex() > model.getTableauPile(location.getPileIndex()).getExposeIndex()))
			{
				//sets the selection to the location and the cards removed from the specified tableau pile where the number removed is 
				//equal to the total number of cards in the pile minus the index of the card that is clicked on
				select = new Selection(location, model.getTableauPile(location.getPileIndex()).removeCards(model.getTableauPile(location.getPileIndex()).getNumCards()-location.getCardIndex()));
			}
			break;
		case WASTE_PILE:
			break;
		default:
			System.err.println("How did you get to this line?");
			break;
		
		}
		return select;
	}

	/**
	 * "Undo" a selection by moving cards from a {@link Selection} object
	 * back to the pile they were taken from, as indicated by the
	 * selection's origin {@link Location}.  For example, if the location
	 * indicates that the selection's cards were taken from a tableau
	 * pile, then they should be moved back to that tableau pile.
	 * 
	 * @param model      the {@link KlondikeModel}
	 * @param selection  the {@link Selection} to undo
	 */
	public void unselect(KlondikeModel model, Selection selection) {
		switch(selection.getOrigin().getLocationType())
		{
		case FOUNDATION_PILE:
			System.err.println("How did you remove cards from a foundation pile?");
			break;
		case MAIN_DECK:
			//adds the selected card back to the main deck (should only be one card if origin is from main deck)
			model.getMainDeck().addCards(selection.getCards());
			break;
		case TABLEAU_PILE:
			//adds the selected card(s) back to the original specified tableau pile.
			model.getTableauPile(selection.getOrigin().getPileIndex()).addCards(selection.getCards());
			break;
		case WASTE_PILE:
			System.err.println("How did you remove cards from the waste pile?");
			break;
		default:
			System.err.println("How did get to this line?");
			break;
		
		}
	}

	/**
	 * <p>Determine whether it is legal to move the current {@link Selection} to a
	 * destination pile indicated by a {@link Location}.</p>
	 * 
	 * <p>If the destination {@link Location} has {@link LocationType#FOUNDATION_PILE}
	 * as its {@link LocationType}, then the {@link Selection} must contain a single card.
	 * If the foundation pile is empty, then the selected card must be an {@link Rank#ACE}.
	 * If the foundation pile is not empty, then the selected card must have the same suit
	 * as the top card on the foundation pile, and the ordinal value of its {@link Rank} must
	 * be one greater than the top card on the foundation pile.</p>
	 * 
	 * <p>If the destination {@link Location} has {@link LocationType#TABLEAU_PILE}
	 * as its {@link LocationType}, then:
	 * <ul>
	 *   <li>If the destination tableau pile is empty, the bottom card of the {@link Selection}
	 *   must be {@link Rank#KING} </li>
	 *   <li>If the destination tableau pile is not empty, the bottom card of the {@link Selection}
	 *   must have a {@link Suit} that is a different {@link Color} than the top card of the
	 *   tableau pile, and the bottom card of the {@link Selection} must have an
	 *   {@link Rank} whose ordinal value is one less than the ordinal value of the
	 *   top card of the tableau pile.</li>
	 * </ul>
	 * </p>
	 * 
	 * <p>If the destination {@link Location} has {@link LocationType#MAIN_DECK} or
	 * {@link LocationType#WASTE_PILE} as its {@link LocationType}, then the move is not legal.</p>
	 * 
	 * <p>
	 * Note that this method just checks to see whether or not a move would
	 * be legal: it does not move any cards.
	 * </p>
	 * 
	 * @param model      the {@link KlondikeModel}
	 * @param selection  the {@link Selection}
	 * @param dest       the destination {@link Location}
	 * @return true if the move is legal, false if the move is not legal
	 */
	public boolean allowMove(KlondikeModel model, Selection selection, Location dest) {
		boolean allow = false;
		switch(dest.getLocationType())
		{
		case FOUNDATION_PILE:
			//first checks to make sure there is only one card selected
			if(selection.getNumCards() == 1)
			{
				//next check if the foundation pile is empty
				if(model.getFoundationPile(dest.getPileIndex()).isEmpty())
				{
					//if foundation pile is empty check to make sure the card is an Ace
					//could have done in one line but split for easier reading
					if(selection.getCards().get(0).getRank().equals(Rank.ACE))
					{
						allow = true;
					}
				}
				//if foundation pile isn't empty check to make sure the suit of the pile is the same as the selected card
				else if(model.getFoundationPile(dest.getPileIndex()).getTopCard().getSuit().equals(selection.getCards().get(0).getSuit()))
				{
					//next check to make sure that the selected card is one higher than the top card in the pile
					if(selection.getCards().get(0).getRank().ordinal() == (model.getFoundationPile(dest.getPileIndex()).getTopCard().getRank().ordinal()+1))
					{
						allow = true;
					}
				}
			}
			break;
		case TABLEAU_PILE:
			//check if tableau pile is empty
			if(model.getTableauPile(dest.getPileIndex()).isEmpty())
			{
				//if empty check to make sure bottom card (index 0) is of rank King
				if(selection.getCards().get(0).getRank().equals(Rank.KING))
				{
					allow = true;
				}
			}
			//if not empty check to make sure suit color is not the same
			else if(!selection.getCards().get(0).getSuit().getColor().equals(model.getTableauPile(dest.getPileIndex()).getTopCard().getSuit().getColor()))
			{
				//next check to make sure the ordinal value of the bottom of the selection is one less than the top of the destination
				if(selection.getCards().get(0).getRank().ordinal() == model.getTableauPile(dest.getPileIndex()).getTopCard().getRank().ordinal()-1)
				{
					allow = true;
				}
			}
			break;
		default:
			break;
		}
		return allow;
		
	}

	/**
	 * <p>Move the cards in a {@link Selection} onto a pile as indicated
	 * by the destination {@link Location}.</p>
	 * 
	 * <p>The method does <em>not</em> need to check whether or not the move is legal:
	 * it can assume that the {@link #allowMove(KlondikeModel, Selection, Location)}
	 * method has already determined that the move is legal.</p>
	 * 
	 * <p>
	 * <strong>Important</strong>: If the pile that the selected cards
	 * were taken from is non-empty, and if its top card is not exposed,
	 * then its top card should be exposed.
	 * </p>
	 * 
	 * <p>
	 * Note that the expose index of the destination pile should not change.
	 * </p>
	 * 
	 * @param model      the {@link KlondikeModel} 
	 * @param selection  the {@link Selection} containing the selected cards
	 * @param dest       the destination {@link Location}
	 */
	public void moveCards(KlondikeModel model, Selection selection, Location dest) {
		//checks for the location type of the destination and then adds the cards to the appropriate location
		switch(dest.getLocationType())
		{
		case FOUNDATION_PILE:
			model.getFoundationPile(dest.getPileIndex()).addCards(selection.getCards());
			break;
		case TABLEAU_PILE:
			model.getTableauPile(dest.getPileIndex()).addCards(selection.getCards());
			break;
		case WASTE_PILE:
			model.getWastePile().addCards(selection.getCards());
			break;
		case MAIN_DECK:
			model.getMainDeck().addCards(selection.getCards());
		default:
			System.err.println("How did you get here?");
			break;
		}
		//checks for the location type of the origin
		//since cards can only be moved from a tableau or the main deck only need to check those
		switch(selection.getOrigin().getLocationType())
		{
		//if tableau and pile isn't empty after move and top card isn't exposed, expose it
		case TABLEAU_PILE:
			int originExpose = model.getTableauPile(selection.getOrigin().getPileIndex()).getExposeIndex();
			int originTopIndex = model.getTableauPile(selection.getOrigin().getPileIndex()).getIndexOfTopCard();
			if(!model.getTableauPile(selection.getOrigin().getPileIndex()).isEmpty() && (originTopIndex < originExpose))
			{
				model.getTableauPile(selection.getOrigin().getPileIndex()).setExposeIndex(model.getTableauPile(selection.getOrigin().getPileIndex()).getIndexOfTopCard());
			}
			break;
		//if main deck isn't empty after move decrement the expose index
		case MAIN_DECK:
			if(!model.getMainDeck().isEmpty())
			{
				model.getMainDeck().setExposeIndex(model.getMainDeck().getExposeIndex()-1);
			}
			break;
		default:
			System.err.println("How did you get to this line?");
			break;
		
		}
	}

	/**
	 * <p>If the main deck is not empty, then remove the top card from the main deck
	 * and add it to the waste pile.  If the main deck is empty, then all cards
	 * should be moved from the waste pile back to the main deck.</p>
	 * 
	 * <p>Note: when the waste pile is recycled, it should move cards
	 * back to the main deck such that they are in the same order in which
	 * they originally occurred.  In other words, drawing all of the cards
	 * from the main deck and then moving them back to the main deck
	 * should result in the main deck being in its original order.</p>
	 * 
	 * <p>Before this method returns, it should check whether the
	 * main deck is non-empty, and if it is non-empty, it should ensure
	 * that the top card on the main deck is exposed.</p>
	 * 
	 * @param model the {@link KlondikeModel}
	 */
	public void drawCardOrRecycleWaste(KlondikeModel model) {
		if(model.getMainDeck().isEmpty())
		{
			ArrayList<Card> temp = model.getWastePile().removeCards(model.getWastePile().getNumCards());
			Collections.reverse(temp);
			model.getMainDeck().addCards(temp);
		}
		else
		{
			model.getWastePile().addCards(model.getMainDeck().removeCards(1));
		}
		if(!model.getMainDeck().isEmpty())
		{
			model.getMainDeck().setExposeIndex(model.getMainDeck().getIndexOfTopCard());
		}
	}

	/**
	 * Determine if the player has won the game.
	 * 
	 * @param model the {@link KlondikeModel}
	 * @return true if each foundation pile has 13 cards, false otherwise
	 */
	public boolean isWin(KlondikeModel model) {
		boolean win = true;
		for(int i = 0; i < model.getFoundations().size(); i++)
		{
			if(model.getFoundationPile(i).getNumCards() != 13)
			{
				win = false;
			}
		}
		return win;
	}
}
