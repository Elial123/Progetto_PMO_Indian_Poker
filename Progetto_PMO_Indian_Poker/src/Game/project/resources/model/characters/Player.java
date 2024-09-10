package Game.project.resources.model.characters;

import Game.project.resources.model.elements.Card;

public class Player extends Person implements IPlayer{
	// attributi interni
	private String name;
	private int bid;
	private boolean fold;
	private int fichesNumber;
	private Card personalCard1,
				 personalCard2;
	
	// costruttore
	public Player() {
		this.fold = false;
		this.fichesNumber = 10;
	}
	
	@Override
	public void setName(String n) {
		this.name = n;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setBid(int bid) {
		this.bid = bid;
	}
	@Override
	public int getBid() {
		return this.bid;
	}
	@Override
	public void setFold(boolean pass) {
		this.fold = pass;
	}
	@Override
	public boolean getFold() {
		return this.fold;
	}
	@Override
	public int getFiches() {
		return this.fichesNumber;
	}
	@Override
	public void setFiches(int fiches) {
		this.fichesNumber = fiches;
	}
	@Override
	public Card getFirstCard() {
		return this.personalCard1;
	}
	@Override
	public Card getSecondCard() {
		return this.personalCard2;
	}
	@Override
	public void setFirstCard(Card c1) {
		this.personalCard1 = c1;
	}
	@Override
	public void setSecondCard(Card c2) {
		this.personalCard2 = c2;
	}
	@Override
	public int combinationType() {
		int typeCombination; 
		
		if(this.personalCard1.getValue() == this.personalCard2.getValue())
			typeCombination = 1;
		else if(this.personalCard1.getSeed() == this.personalCard2.getSeed())
			typeCombination = 2;
		else typeCombination = 3;
		return typeCombination;
	}
	
	@Override
	public String toString() {
		return " Player: "+this.getName()+" numero di fiches: "+this.fichesNumber
				+"\n carte: "+this.personalCard1;
	}
}
