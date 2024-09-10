package Game.project.resources.model.characters;

import Game.project.resources.model.elements.Card;

public interface IPlayer {
	public void setName(String n);
	public String getName();
	public void setBid(int bid);
	public int getBid();
	public void setFold(boolean pass);
	public boolean getFold();
	public int getFiches();
	public void setFiches(int fiches);
	public Card getFirstCard();
	public Card getSecondCard();
	public void setFirstCard(Card c1);
	public void setSecondCard(Card c2);
	public int combinationType();
	public String toString();
}
