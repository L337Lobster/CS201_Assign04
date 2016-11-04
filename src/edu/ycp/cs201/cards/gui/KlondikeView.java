package edu.ycp.cs201.cards.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.ycp.cs201.cards.Card;
import edu.ycp.cs201.cards.KlondikeController;
import edu.ycp.cs201.cards.KlondikeModel;
import edu.ycp.cs201.cards.Location;
import edu.ycp.cs201.cards.LocationType;
import edu.ycp.cs201.cards.Pile;
import edu.ycp.cs201.cards.Selection;
import edu.ycp.cs201.cards.StringifyGameState;

public class KlondikeView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/** Width of the window */
	private static final int WIDTH = 800;
	
	/** Height of window */
	private static final int HEIGHT = 600;
	
	/** Width of card images */
	private static final int CARD_WIDTH = 80;
	
	/** Height of card images */
	private static final int CARD_HEIGHT = 116;
	
	/** Offset of piles from left edge */
	private static final int LEFT_OFFSET = 30;
	
	/** Offset of top-row piles from top edge */
	private static final int TOP_OFFSET = 20;

	/** Offset of tableau piles from left edge */
	private static final int FOUNDATION_LEFT_OFFSET = 360;

	/** Offset of tableau piles from top edge */
	private static final int TABLEAU_TOP_OFFSET = 160;

	/** Spacing of piles horizontally */
	private static final int HORIZONTAL_PILE_SPACING = 110;

	/** Vertical spacing of cards in tableau piles */
	public static final int VERTICAL_CARD_SPACING = 24;

	private KlondikeModel model;
	private KlondikeController controller;
	
	private CardImageCollection cardImageCollection;
	
	private Selection selected;
	private Point mousePos, cardClicked;
	
	public KlondikeView() {
		setBackground(new Color(0, 100, 0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		cardImageCollection = new CardImageCollection();
		selected = null;
		mousePos = new Point(WIDTH/2, HEIGHT/2);
		cardClicked = new Point(WIDTH/2, HEIGHT/2);
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				handleMousePressed(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				handleMouseDragged(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				handleMouseReleased(e);
			}
		};
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	public void setModel(KlondikeModel model) {
		this.model = model;
	}
	
	public void setController(KlondikeController controller) {
		this.controller = controller;
	}
	
	protected void handleMousePressed(MouseEvent e) {
		//check if mouse is vertically in the first row of piles
		//this includes the main pile, the waste pile, and the foundation piles
		mousePos = new Point(e.getX(), e.getY());
		cardClicked = new Point(e.getX(), e.getY());
		if(isFirstRow(e.getY()))
		{
			int leftBound = LEFT_OFFSET;
			int rightBound = leftBound+CARD_WIDTH;
			for(int i = 0; i < 6; i++)
			{
				if(e.getX() > leftBound && e.getX() < rightBound)
				{
					switch(i)
					{
					case 0:
						selected = controller.select(model, new Location(LocationType.MAIN_DECK, 0, model.getMainDeck().getIndexOfTopCard()), cardClicked);
						break;
					case 1:
						controller.drawCardOrRecycleWaste(model);
						break;
					case 2:
						System.out.println("F1 pile, " + leftBound + " " + rightBound);
						break;
					case 3:
						System.out.println("F2 pile, " + leftBound + " " + rightBound);
						break;
					case 4:
						System.out.println("F3 pile, " + leftBound + " " + rightBound);
						break;
					case 5:
						System.out.println("F4 pile, " + leftBound + " " + rightBound);
						break;
					}
				}
				if(i == 1)
				{
					leftBound = FOUNDATION_LEFT_OFFSET;
					rightBound = leftBound + CARD_WIDTH;
				}
				else
				{
					leftBound += HORIZONTAL_PILE_SPACING;
					rightBound += HORIZONTAL_PILE_SPACING;
				}
			}
		}
		else if(isTableauRow(e.getY()))
		{
			//set left bound at the left offset
			int leftBound = LEFT_OFFSET;
			//set the right bound at the left offset plus the width of the card
			int rightBound = leftBound+CARD_WIDTH;
			//j is used to calculate the card index
			int j = 0;
			for(int i = 0; i < 7; i++)
			{
				//if the x value is between the left and right bounds continue
				if(mousePos.x > leftBound && mousePos.x < rightBound)
				{
					//if mouse is within a specified tableau pile continue
					if(withinTableau(mousePos.y, i))
					{
						//j is calculated based on the current y subtracted by the tableau top offset then dividing by the vertical spacing
						j = (mousePos.y-TABLEAU_TOP_OFFSET)/VERTICAL_CARD_SPACING;
						//select the section of cards based on where it was clicked
						selected = controller.select(model, new Location(LocationType.TABLEAU_PILE, i, j), cardClicked);
					}
				}
				//change the bounds for testing the next tableau pile
				leftBound += HORIZONTAL_PILE_SPACING;
				rightBound += HORIZONTAL_PILE_SPACING;
			}
		}
		repaint();
	}
	/**
	 * Checks if mouse is within a specified tableau
	 * @param y Y coord of the mouse
	 * @param i specified tableau index
	 * @return true if mouse is within specified tableau
	 */
	protected boolean withinTableau(int y, int i)
	{
		int yMax = ((model.getTableauPile(i).getNumCards()==0) ? VERTICAL_CARD_SPACING : ((model.getTableauPile(i).getNumCards()-1)*VERTICAL_CARD_SPACING)+CARD_HEIGHT + TABLEAU_TOP_OFFSET);
		//System.out.println(yMax + " " + y);
		return (y <= yMax);
	}
	protected boolean isFirstRow(int y)
	{
		return ((y > TOP_OFFSET) && (y < TOP_OFFSET+CARD_HEIGHT));
	}
	protected boolean isTableauRow(int y)
	{
		return (y >= TABLEAU_TOP_OFFSET);
	}
	protected void handleMouseDragged(MouseEvent e) {
		mousePos = new Point(e.getX(), e.getY());
		repaint();
	}

	protected void handleMouseReleased(MouseEvent e) {
		if(selected != null)
		{
			if(isFirstRow((int) mousePos.getY()))
			{
				int whichFound = checkWhichFoundation();
				System.out.println(whichFound);
				System.out.println(mousePos.getX() + " " + mousePos.getY());
				if(controller.allowMove(model, selected, new Location(LocationType.FOUNDATION_PILE, whichFound, 0)))
				{
					controller.moveCards(model, selected, new Location(LocationType.FOUNDATION_PILE, whichFound, 0));
				}
				else
				{
					controller.unselect(model, selected);
				}
			}
			else if(isTableauRow((int) mousePos.getY()))
			{
				int whichTab = checkWhichTableau();
				if(controller.allowMove(model, selected, new Location(LocationType.TABLEAU_PILE, whichTab, 0)))
				{
					controller.moveCards(model, selected, new Location(LocationType.TABLEAU_PILE, whichTab, 0));
				}
				else
				{
					controller.unselect(model, selected);
				}
			}
			else
			{
				controller.unselect(model, selected);
			}
			selected = null;
		}	
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Paint background
		super.paintComponent(g);
		
		// Paint main deck (showing top card)
		drawPile(g, LEFT_OFFSET, TOP_OFFSET, model.getMainDeck());
		
		// Paint waste pile
		//System.out.printf("Waste pile expose index=%d\n", model.getWastePile().getExposeIndex());
		drawPile(g, LEFT_OFFSET + HORIZONTAL_PILE_SPACING, TOP_OFFSET, model.getWastePile());
		drawValidOutline(g);
		// Paint foundation piles (showing top card)
		for (int i = 0; i < 4; i++) {
			drawPile(g, FOUNDATION_LEFT_OFFSET + i*HORIZONTAL_PILE_SPACING, TOP_OFFSET, model.getFoundationPile(i));
		}
		// Paint tableau piles
		for (int i = 0; i < 7; i++) {
			drawTableauPile(g, LEFT_OFFSET + i*HORIZONTAL_PILE_SPACING, TABLEAU_TOP_OFFSET, model.getTableauPile(i));
		}
		if(selected != null)
		{
			int numCards = selected.getNumCards();
			for (int i = 0; i < numCards; i++) {
				Card card = selected.getCards().get(i);
				BufferedImage img = cardImageCollection.getFrontImage(card);
				g.drawImage(img, mousePos.x-selected.getxOffset(), mousePos.y - selected.getyOffset() + i*VERTICAL_CARD_SPACING, null);
			}
		}
		if(controller.isWin(model))
		{
			String gameOverText = "Winner!";
			g.setFont(new Font("Dialog", Font.BOLD, 48));
			int alpha = 200;
			g.setColor(new Color(0, 0, 0, alpha));
			int x = (WIDTH/2)-(g.getFontMetrics().stringWidth(gameOverText)/2), y = (HEIGHT/2)-24;
			g.fillRect(x-2, y-42, g.getFontMetrics().stringWidth(gameOverText), 48);
			g.setColor(Color.WHITE);
			g.drawString(gameOverText, x, y);
		}
	}
	private int checkWhichTableau()
	{
		int tabPos = 0;
		//set left bound at the left offset
		int leftBound = LEFT_OFFSET;
		//set the right bound at the left offset plus the width of the card
		int rightBound = leftBound+CARD_WIDTH;
		//j is used to calculate the card index
		for(int i = 0; i < model.getTableaus().size(); i++)
		{
			if(mousePos.x > leftBound && mousePos.x < rightBound)
			{
				tabPos = i;
			}
			leftBound += HORIZONTAL_PILE_SPACING;
			rightBound += HORIZONTAL_PILE_SPACING; 
		}
		return tabPos;
	}
	private int checkWhichFoundation()
	{
		int foundationPos = 0;
		//set left bound at the left offset
		int leftBound = FOUNDATION_LEFT_OFFSET;
		//set the right bound at the left offset plus the width of the card
		int rightBound = leftBound+CARD_WIDTH;
		//j is used to calculate the card index
		for(int i = 0; i < 4; i++)
		{
			if(mousePos.x > leftBound && mousePos.x < rightBound)
			{
				foundationPos = i;
			}
			leftBound += HORIZONTAL_PILE_SPACING;
			rightBound += HORIZONTAL_PILE_SPACING; 
		}
		return foundationPos;
	}
	private void drawPile(Graphics g, int x, int y, Pile pile) {
		if (pile.isEmpty()) {
			// Draw outline
			g.setColor(Color.LIGHT_GRAY);
			g.drawRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, 12, 12);
		} else {
			// Draw image of top card (or card back image if top card is not exposed)
			Card top = pile.getTopCard();
			int indexOfTopCard = pile.getIndexOfTopCard();
			int exposeIndex = pile.getExposeIndex();
			//System.out.printf("topcard=%d,expose=%d\n", indexOfTopCard, exposeIndex);
			boolean isExposed = indexOfTopCard >= exposeIndex;
			BufferedImage img = isExposed ? cardImageCollection.getFrontImage(top) : cardImageCollection.getBackImage();
			g.drawImage(img, x, y, null);
		}
	}
	private void drawValidOutline(Graphics g)
	{
		//set left bound at the left offset
		int leftBound = LEFT_OFFSET;
		//set the right bound at the left offset plus the width of the card
		int rightBound = leftBound+CARD_WIDTH;
		g.setColor(Color.GREEN);
		for(int i = 0; i < 7; i++)
		{
			//if the x value is between the left and right bounds continue
			if(mousePos.x > leftBound && mousePos.x < rightBound)
			{
				//if mouse is within a specified tableau pile continue
				if(selected != null)
				{
					if(withinTableau(mousePos.y, i) && controller.allowMove(model, selected, new Location(LocationType.TABLEAU_PILE, i, model.getTableauPile(i).getIndexOfTopCard())))
					{
						g.fillRoundRect((LEFT_OFFSET + i*HORIZONTAL_PILE_SPACING)-5, TABLEAU_TOP_OFFSET-5, CARD_WIDTH+10, CARD_HEIGHT+10+(((model.getTableauPile(i).getNumCards()==0) ? 0 : (model.getTableauPile(i).getNumCards()-1))*VERTICAL_CARD_SPACING), 12, 12);
					}
				}
			}
			//change the bounds for testing the next tableau pile
			leftBound += HORIZONTAL_PILE_SPACING;
			rightBound += HORIZONTAL_PILE_SPACING;
		}
		leftBound = FOUNDATION_LEFT_OFFSET;
		rightBound = leftBound+CARD_WIDTH;
		for(int i = 0; i < 4; i++)
		{
			if(mousePos.x > leftBound && mousePos.x < rightBound)
			{
				if(selected != null)
				{
					if(i < 4 && isFirstRow(mousePos.y) && controller.allowMove(model, selected, new Location(LocationType.FOUNDATION_PILE, i, model.getFoundationPile(i).getIndexOfTopCard())))
					{
						g.fillRoundRect((FOUNDATION_LEFT_OFFSET + i*HORIZONTAL_PILE_SPACING)-5, TOP_OFFSET-5, CARD_WIDTH+10, CARD_HEIGHT+10, 12, 12);
					}
				}
			}
			leftBound += HORIZONTAL_PILE_SPACING;
			rightBound += HORIZONTAL_PILE_SPACING;
		}
	}
	private void drawTableauPile(Graphics g, int x, int y, Pile tableauPile) {
		// Draw cards from bottom of pile towards top.
		// All cards whose indices are greater than or equal to
		// the pile's expose index are drawn face-up.
		int numCards = tableauPile.getNumCards();
		for (int i = 0; i < numCards; i++) {
			Card card = tableauPile.getCard(i);
			BufferedImage img = i >= tableauPile.getExposeIndex()
					? cardImageCollection.getFrontImage(card)
					: cardImageCollection.getBackImage();
			g.drawImage(img, x, y + i*VERTICAL_CARD_SPACING, null);
		}
	}
}
