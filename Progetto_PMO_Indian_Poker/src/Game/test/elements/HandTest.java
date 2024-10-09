package Game.test.elements;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Hand;
import Game.project.resources.model.enums.Suits;

class HandTest {
	private Hand hand;
	private Integer value1,
					value2;
	private Card card1,
			     card2;
	
	@BeforeEach                                               
    void setUp() {
		hand = new Hand();
		value1 = (int)(Math.random() * (9 - 0)) + 0;
		card1 = new Card(value1, Suits.CUORI);
		value2 = (int)(Math.random() * (9 - 0)) + 0;
		card2 = new Card(value2, Suits.CUORI);
    }

	// test metodo getCards()
	@Test 
	void testGetCardsAndSetCards() {
		hand.setCards(card1, card2);
		Assert.assertNotNull(hand.getCards());
		Assert.assertEquals(2, hand.getCards().size());
	}
	

	// test metodo getRankFirstCard() e getRankSecondCard()
	@Test 
	void testGetRanks() {
		hand.setCards(card1, card2);
		Assert.assertEquals(value1, hand.getRankFirstCard());
		Assert.assertEquals(value2, hand.getRankSecondCard());
	}
	
	// test metodo getSuitFirstCard() e getSuitSecondCard()
	@Test 
	void testGetSuitss() {
		hand.setCards(card1, card2);
		Assert.assertEquals(Suits.CUORI, hand.getSuitFirstCard());
		Assert.assertEquals(Suits.CUORI, hand.getSuitSecondCard());
	}
	
	// test metodo hsPair()
	@Test
	void testHasPair() {
		hand.setCards(card1, card2);
		if(value1 == value2)
			Assert.assertTrue(hand.hasPair());
		else
			Assert.assertFalse(hand.hasPair());
	}
	
	// test metodo hasSameSuit()
	@Test
	void testHasSameSuit() {
		hand.setCards(card1, card2);
		Assert.assertTrue(hand.hasSameSuit());
	}
	// test toString()
	@Test
	void testToString() {
		hand.setCards(card1, card2);
		Assert.assertEquals(card1.getRank()+"-"+card1.getSuit()+", "+
				card2.getRank()+"-"+card2.getSuit(), hand.toString());
	}

}
