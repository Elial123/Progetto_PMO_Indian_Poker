package Game.project.resources.model.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Game.project.resources.model.enums.Suits;


public class Deck implements IDeck{
	private List<Card> cards;
    private List<Card> extractedCards;
    
    public Deck() {
        cards = new ArrayList<>();
        extractedCards = new ArrayList<>();
        // Crea 40 carte (1-10 per ogni seme)
        for (Suits suit : Suits.values()) {
            for (int rank = 1; rank <= 10; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }
    
    @Override
    public Card getCard() {
    	int i;
    	Random random = new Random();
    	do{			
    		i = random.nextInt(40); // 40 é il limite superiore è esclusivo
		}
		while(extractedCards.contains(cards.get(i)));//se è già contenuta nella lista riestrae
    	extractedCards.add(cards.get(i)); // aggiunge nuova carta
	    //System.out.println("j: "+j);
		return cards.get(i);
    }

    @Override
    public void reEnteringCards() {
    	this.extractedCards.clear();
    }
}