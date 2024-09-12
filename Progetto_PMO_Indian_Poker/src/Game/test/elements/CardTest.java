package Game.test.elements;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.Card;

public class CardTest {
	private Card card;
	private int seed,
				value;
	
	@BeforeEach                                               
    void setUp() {
		value = (int)(Math.random() * (9 - 0)) + 0;
		seed = (int)(Math.random() * (3 - 0)) + 0;
		card = new Card(value, seed);
    }
	
	// test costruttore e metodo getValue()
	@Test
	void testGetValue() {
		Assert.assertEquals(value, card.getValue());
	}
	
	// test metodo get
	@Test
	void testGetSeed() {
		Assert.assertEquals(seed, card.getSeed());
	}
	
	// test metodo toString()
	@Test
	void testToString() {
		String[] values ={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		String[] seeds ={"quadri","cuori","fiori","picche"};
		
		String str = values[value] + " di " + seeds[seed];
		
		Assert.assertEquals(str, card.toString());
	}

}
