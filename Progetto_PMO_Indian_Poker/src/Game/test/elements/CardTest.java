package Game.test.elements;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.Card;

public class CardTest {
	private Card card;
	
	@BeforeEach                                               
    void setUp() {
		card = new Card((int)(Math.random() * (9 - 0)) + 0, (int)(Math.random() * (3 - 0)) + 0);
    }
	
	
	// test costruttore e metodo getValue()
	@Test
	void testGetValue() {
		Assert.assertNotNull(card.getValue());
	}
	
	// test metodo get
	@Test
	void testGetSeed() {
		Assert.assertNotNull(card.getSeed());
	}
	
	@Test
	void testToString() {
		Assert.assertNotNull(card.toString());
	}

}
