package Game.project.resources.model.elements;

import Game.project.resources.model.enums.Suits;

public class Card implements ICard{
	private final Integer rank;
    private final Suits suit;  

    public Card(Integer rank, Suits suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    @Override
    public Integer getRank() {
        return rank;
    }
    
    @Override
    public Suits getSuit() {
        return suit;
    }
    
    @Override
    public String toString() {
    	return "img/"+this.getSuit()+"/"+this.getRank()+"-"+this.getSuit()+".jpg";
    }
}
