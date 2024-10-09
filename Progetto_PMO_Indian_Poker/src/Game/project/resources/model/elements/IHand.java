package Game.project.resources.model.elements;

import java.util.List;

import Game.project.resources.model.enums.Suits;

public interface IHand {
	
	List<Card> getCards();
	
	void setCards(Card c1, Card c2);
	
	boolean hasPair();
	
	boolean hasSameSuit();
	
	Integer getRankFirstCard();
	
	Integer getRankSecondCard();
	
	Suits getSuitFirstCard();
	
	Suits getSuitSecondCard();
	
	String toString();
}
