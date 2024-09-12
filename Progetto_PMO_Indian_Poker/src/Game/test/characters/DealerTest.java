package Game.test.characters;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.Dealer;
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
		
		c = dealer.distribute();
		Assert.assertNotNull(c);
	}
	
	// test metodo winner()
	@Test
	void testWinner() {
		int winnerNumber;
		Card c;
		Player p = new Player(),
			   p1 = new Player();
		ArrayList<Player> characters = new ArrayList<Player>();
		
		c = dealer.distribute();
		p.setFirstCard(c);
		c = dealer.distribute();
		p.setSecondCard(c);
		
		c = dealer.distribute();
		p1.setFirstCard(c);
		c = dealer.distribute();
		p1.setSecondCard(c);
		
		characters.add(p);
		characters.add(p1);
		
		winnerNumber = dealer.winner(characters);
		Assert.assertNotNull(winnerNumber);
		
		p.setFold(true);
		p1.setFold(true);
		
		Assert.assertEquals(-1, dealer.winner(characters));
	}
	
	// test metodo calculateNumberOfFiches()
	@Test
	void testcCalculateNumberOfFiches() {
		int winnerNumber,
			fichesWon;
		Player p = new Player(),
			   p1 = new Player();
		ArrayList<Player> characters = new ArrayList<Player>();
		
		p.setFiches(10);
		p1.setFiches(10);
		p.setBid(2);
		p1.setBid(3);
		
		winnerNumber = 0;
		fichesWon = p1.getBid();
		
		characters.add(p);
		characters.add(p1);
		
		dealer.calculateNumberOfFiches(characters, winnerNumber, fichesWon);
		Assert.assertEquals(13, p.getFiches());
		Assert.assertEquals(7, p1.getFiches());
	}
	
	// test metodo toString()
	@Test
	void testToString() {
		Assert.assertEquals("Dealer", dealer.toString());
	}
}
