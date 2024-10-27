package Game.project.resources.model.characters;

import java.util.List;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Hand;


public abstract class Player{
	// attributi interni
	private String name;
	private int bid;
	private boolean fold;
	private int fichesNumber;
	private Hand hand;
	private boolean pairChoice;
	
	// costruttore
	public Player() {
		this.hand = new Hand();
		this.fold = false;
		this.fichesNumber = 10;
		this.pairChoice = false;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getChoice() {
		return this.pairChoice;
	}
	
	public void setChoice(boolean choice) {
		this.pairChoice = choice;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	public int getBid() {
		return this.bid;
	}
	
	public void setFold(boolean fold) {
		this.fold = fold;
	}
	
	public boolean getFold() {
		return this.fold;
	}
	
	public int getFiches() {
		return this.fichesNumber;
	}
	
	public void setFiches(int fiches) {
		this.fichesNumber = fiches;
	}
	
	public abstract void makeMove(List<Player> players);// arg in PokerDecision
	
	public Hand getHand() {
        return hand;
    }
    
	public void setHand(Card c1, Card c2) {
        this.hand.setCards(c1, c2);
    }
    
	public int combinationType() {
		int typeCombination; 
		
		if(this.hand.hasPair())
			typeCombination = 3;
		else if(this.hand.hasSameSuit())
			typeCombination = 2;
		else typeCombination = 1;
		return typeCombination;
	}
	
	
	@Override
	public String toString() {
		return " Player: "+this.getName()+" fiches: "+this.fichesNumber+" bid: "+this.bid;
	}
}
