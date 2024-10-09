package Game.project.resources.model.elements;

import java.util.ArrayList;
import java.util.List;

import Game.project.resources.model.enums.Suits;

public class Hand implements IHand{
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }
    
    @Override
    public void setCards(Card c1, Card c2) {
    	this.cards.clear();
        this.cards.add(c1);
        this.cards.add(c2);
    }

    @Override
    public boolean hasPair() {
        return cards.get(0).getRank().equals(cards.get(1).getRank());
    }

    @Override
    public boolean hasSameSuit() {
        return cards.get(0).getSuit().equals(cards.get(1).getSuit());
    }

    @Override
    public Integer getRankFirstCard() {
        return cards.get(0).getRank();
    }
    
    @Override
    public Integer getRankSecondCard() {
        return cards.get(1).getRank();
    }
    
    public Suits getSuitFirstCard() {
        return cards.get(0).getSuit();
    }
    
    @Override
    public Suits getSuitSecondCard() {
        return cards.get(1).getSuit();
    }
    
    @Override
    public String toString() {
    	return this.cards.get(0).getRank()+"-"+this.cards.get(0).getSuit()+", "+
    			this.cards.get(1).getRank()+"-"+this.cards.get(1).getSuit();
    }
}
