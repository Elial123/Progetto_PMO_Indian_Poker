package Game.test.elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import Game.project.resources.model.elements.Deck;
import Game.project.resources.model.elements.Card;

class DeckTest {
	private Deck deck;
	Card c,
	 	 c1;
	
	@BeforeEach                                         
    void setUp() {
		deck = new Deck();
    }
	
	
	// test costruttore e metodo getCard()
	@RepeatedTest(20)
	public void testGetCard() {
		c = deck.getCard();
		System.out.println(c);
		c1 = deck.getCard();
		System.out.println(c1);
		//System.out.println("carta estratta: "+c1);
		if (c == null || c1 == null)
			fail("fallimento nell'estrazione delle carte");
		else
		if (c.getSuit() == c1.getSuit() && c.getRank() == c1.getRank()) {
			fail("carta estratta due volte");
		}	
		
		
	}
}
