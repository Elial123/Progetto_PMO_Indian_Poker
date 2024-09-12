package Game.test.characters;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Deck;

class PlayerTest {
	private Player player;
	
	@BeforeEach
    public void setUp() {
       player = new Player();
    }
	
	// test metodi getName() e setName()
	@Test
	void testGetAndSetName() {
		player.setName("Federico");
		Assert.assertEquals("Federico", player.getName());
	}
	// test metodo setBid() e getBid()
	@Test
	void testGetAndSetBid() {
		player.setBid(9);
		Assert.assertEquals(9, player.getBid());
	}
	// test metodo setFold() e getFold()
	@Test
	void testGetAndSetFold() {
		player.setFold(false);
		Assert.assertEquals(false, player.getFold());
	}
	// test metodo setFiches() e getFiches()
	@Test
	void testGetAndSetFiches() {
		player.setFiches(9);
		Assert.assertEquals(9, player.getFiches());
	}
	// test getFirstCard(), getSecondCard(), setFirstCard(), setSecondCard(), combinationType()
	@Test
	void testCombinationOfCards() {
		Deck deck = new Deck();
		Card c1,
			 c2;
		
		c1 = deck.getCard();
		c2 = deck.getCard();
		player.setFirstCard(c1);
		player.setSecondCard(c2);
		Assert.assertEquals(c1, player.getFirstCard());
		Assert.assertEquals(c2, player.getSecondCard());
		Assert.assertNotNull(player.combinationType());
	}
	
	// test metodo toString()
		@Test
		void testToString() {
			String str = " Player: "+player.getName()+" numero di fiches: "+player.getFiches()
					+"\n carte: "+player.getFirstCard();
			
			Assert.assertEquals(str, player.toString());
		}
}
