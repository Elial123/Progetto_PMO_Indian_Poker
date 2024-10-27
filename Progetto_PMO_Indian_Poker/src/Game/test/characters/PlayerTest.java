package Game.test.characters;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import Game.project.resources.model.characters.BotPlayer0;
import Game.project.resources.model.characters.BotPlayer;
import Game.project.resources.model.characters.HumanPlayer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Deck;

class PlayerTest {
	private Player player1,
				   player2;
	
	@BeforeEach
    public void setUp() {
       player1 = new HumanPlayer();
       player2 = new BotPlayer();
    }
	
	// test metodi getName() e setName()
	@Test
	void testGetAndSetName() {
		player1.setName("Federico");
		Assert.assertEquals("Federico", player1.getName());
		player2.setName("Federico");
		Assert.assertEquals("Federico", player2.getName());
	}
	// test metodo setBid() e getBid()
	@Test
	void testGetAndSetBid() {
		player1.setBid(9);
		Assert.assertEquals(9, player1.getBid());
		player2.setBid(9);
		Assert.assertEquals(9, player2.getBid());
	}
	// test metodo setFold() e getFold()
	@Test
	void testGetAndSetFold() {
		player1.setFold(false);
		Assert.assertEquals(false, player1.getFold());
		player2.setFold(false);
		Assert.assertEquals(false, player2.getFold());
	}
	// test metodo setFiches() e getFiches()
	@Test
	void testGetAndSetFiches() {
		player1.setFiches(9);
		Assert.assertEquals(9, player1.getFiches());
		player2.setFiches(9);
		Assert.assertEquals(9, player2.getFiches());
	}
	
	// test makeMove()
	@Test
	void testMakeMove() {
		Deck deck = new Deck();
		Card c1,
			 c2;
		List<Player> list = new ArrayList<>();
		Player player3 = new BotPlayer();
		Player player4 = new BotPlayer();
		
		c1 = deck.getCard();
		c2 = deck.getCard();
		player1.setHand(c2, c1);
		player2.setHand(c1, c2);
		player3.setHand(c1, c2);
		player4.setHand(c1, c2);
		player1.setName("Player");
		player2.setName("bot1");
		player3.setName("bot2");
		player4.setName("bot3");
		
		list.add(player1);
		list.add(player2);
		list.add(player3);
		list.add(player4);
		
		//Match2 match = new Match2(dealer, player1, player2, player3, player4);
		//match.payTaxes(); // è necessario che paghino la tassa
		
		player2.makeMove(list);
		
		Assert.assertNotNull(player2.getBid());
		System.out.println(player2.getBid());
		if(player2.getBid() < 0) // la tassa viene gestita dal Match
			fail("Errore");
		
		player1.setChoice(true);
		
		player2.makeMove(list);
		
		Assert.assertNotNull(player2.getBid());
		System.out.println(player2.getBid());
		if(player2.getBid() < 0)
			fail("Errore");
		
	}
	// test getFirstCard(), getSecondCard(), setFirstCard(), setSecondCard(), combinationType()
	@Test
	void testCombinationOfCards() {
		Deck deck = new Deck();
		Card c1,
			 c2;
		
		c1 = deck.getCard();
		c2 = deck.getCard();
		player1.setHand(c1, c2);
		player2.setHand(c1, c2);
		Assert.assertEquals(c1, player1.getHand().getCards().get(0));
		Assert.assertEquals(c2, player2.getHand().getCards().get(1));
		Assert.assertNotNull(player1.combinationType());
		Assert.assertNotNull(player2.combinationType());
		Assert.assertEquals(player1.combinationType(), player2.combinationType());
	}
	
	// test metodo toString()
	@Test
	void testToString() {
		String str = " Player: "+player1.getName()+" fiches: "+player1.getFiches()+" bid: "+player1.getBid();
			
		Assert.assertEquals(str, player1.toString());
		
		str = " Player: "+player2.getName()+" fiches: "+player2.getFiches()+" bid: "+player2.getBid();

		Assert.assertEquals(str, player2.toString());
	}
}