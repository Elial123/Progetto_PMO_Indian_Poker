package Game.test.characters;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.BotPlayer;
import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.HumanPlayer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.Card;

class DealerTest {
	private Dealer dealer;
	
	@BeforeEach                                               
    void setUp() {
		dealer = Dealer.getInstance();
	}
	
    // test metodo distribution()
	@Test
	void testDistribution() {
		Card c;
		
		// prendo carta
		c = dealer.distribute();
		
		// controllo non sia null
		Assert.assertNotNull(c);
	}
	
	// test metodo winner()
	@Test
	void testWinner() {
		int winnerNumber;
		Card c1,
		     c2;
		Player p = new HumanPlayer(),
			   p1 = new BotPlayer();
		ArrayList<Player> characters = new ArrayList<Player>();
		
		// set hand di p e p1
		c1 = dealer.distribute();
		c2 = dealer.distribute();
		p.setHand(c1, c2);
		
		c1 = dealer.distribute();
		c2 = dealer.distribute();
		p1.setHand(c1, c2);
		
		characters.add(p);
		characters.add(p1);
		
		// ricavo il vincitore
		winnerNumber = dealer.winner(characters);
		// mi assicuro che esista
		Assert.assertNotNull(winnerNumber);
		
		// setto entrambi a fold
		p.setFold(true);
		p1.setFold(true);
		
		// controllo comportamento nel caso tutti foldino
		Assert.assertEquals(-1, dealer.winner(characters));
	}
	
	// test metodo calculateNumberOfFiches()
	@Test
	void testcCalculateNumberOfFiches() {
		int winnerNumber,
			fichesWon;
		Player p = new HumanPlayer(),
				   p1 = new BotPlayer();
		ArrayList<Player> characters = new ArrayList<Player>();
		
		// setto le fiches
		p.setFiches(10);
		p1.setFiches(10);
		p.setBid(2);
		p1.setBid(3);
		
		// setto vincitore
		winnerNumber = 0;
		fichesWon = p1.getBid();
		
		characters.add(p);
		characters.add(p1);
		
		// calcolo fiches
		dealer.calculateNumberOfFiches(characters, winnerNumber, fichesWon);
		Assert.assertEquals(13, p.getFiches());
		Assert.assertEquals(7, p1.getFiches());
	}
	
	// test metodo toString()
	@Test
	void testToString() {
		Assert.assertEquals("John", dealer.toString());
	}
}
