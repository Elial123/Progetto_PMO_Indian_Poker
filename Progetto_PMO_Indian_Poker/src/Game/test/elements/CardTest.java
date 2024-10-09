package Game.test.elements;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.enums.Suits;

public class CardTest {
	private Card card;
	private Integer value;
	
	@BeforeEach                                               
    void setUp() {
		value = (int)(Math.random() * (9 - 0)) + 0;
		card = new Card(value, Suits.CUORI);
    }
	
	// test costruttore e metodo getValue()
	@Test
	void testGetValue() {
		Assert.assertEquals(value, card.getRank());
	}
	
	// test metodo get
	@Test
	void testGetSeed() {
		Assert.assertEquals(Suits.CUORI, card.getSuit());
	}
	
	// test metodo toString()
	@Test
	void testToString() {
		
		String str = "img/"+card.getSuit()+"/"+card.getRank()+"-"+card.getSuit()+".jpg";;
		
		Assert.assertEquals(str, card.toString());
	}

}
