package Game.test.match;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.enums.Result;
import Game.project.resources.model.match.BidIncorrectException;
import Game.project.resources.model.match.Match;

class TestMatch {
	private Match match;
	private Dealer dealer;
	private Player p1, 
				   p2, 
				   p3, 
				   p4;
	
	@BeforeEach
    public void setUp() {
       dealer = Dealer.getInstance();
       p1 = new Player();
       p2 = new Player();
       p3 = new Player();
       p4 = new Player();
       
       match = new Match(dealer, p1, p2, p3, p4);
    }
	
	// test metodo getTimer()
	@Test
	void testGetGameTimer() {
		Assert.assertNotNull(match.getTimer());
	}
	// test metodo start(String name) 
	@Test
	void testStart() {
		match.start("elia");
		Assert.assertEquals("elia", p1.getName());
		
	}
	// test metodo distribution() 
	@Test
	void testDistribution() {
		match.start("elia");
		match.distribution();
		Assert.assertNotNull(p1.getFirstCard());
		Assert.assertNotNull(p1.getSecondCard());
	}
	
	// test metodo situationPlayer() 
	@Test
	void testSituationPlayer() {
		match.start("elia");
		match.distribution();
		Assert.assertNotNull(match.situationPlayer());
	}
	// test metodo payTaxes() 
	@Test
	void testPayTaxes() {
		match.start("elia");
		match.payTaxes();
		Assert.assertEquals(1, p1.getBid());
	}
	// test metodo fold(int index) 
	@Test
	void testFold() {
		match.start("elia");
		match.fold(0);
		Assert.assertEquals(true, p1.getFold());
	}
	// test metodo bid(int b) 
	@Test
	void testBid() throws BidIncorrectException {
		System.out.println("\n\ntestBid-------------------------------------\n");
		match.start("elia");
		match.bid(4);
		Assert.assertEquals(5, p1.getBid()); // la tassa 1 + 5 la bid
		Assert.assertNotNull(p2.getBid());
		
		assertThrows(BidIncorrectException.class, () -> {
			match.bid(5);
        });
		
	}
	// test metodo roundWinner()
	@Test
	void testRoundWinner() throws BidIncorrectException {
		System.out.println("\n\ntestRoundWinner-------------------------------------\n");
		match.start("elia");
		match.distribution();
		match.bid(4);
		match.roundWinner();
		Assert.assertNotEquals(10, p1.getFiches());
		// controllo sul tutti foldano...
	}
	// test metodo roundOver()
	@Test
	void testRoundOver() throws BidIncorrectException {
		System.out.println("\n\ntestRoundOver-------------------------------------\n");
		match.start("elia");
		match.distribution();
		match.bid(1);
		match.roundWinner();
		
		Assert.assertNotNull(match.roundOver());
		System.out.println("result: "+match.roundOver());
		
		p1.setFiches(0);
		Assert.assertEquals(Result.YOU_lOST, match.roundOver());
		System.out.println("result: "+match.roundOver());
		p1.setFiches(40);
		p2.setFiches(0);
		p3.setFiches(0);
		p4.setFiches(0);
		
		match.bid(1);
		match.roundWinner();
		System.out.println("result: "+" "+match.situationPlayer());
		Assert.assertEquals(Result.YOU_WON, match.roundOver());
		
		System.out.println("result: "+match.roundOver());
		
	}
	// test metodo gameWinner()
	@Test
	void testGameWinner() throws BidIncorrectException {
		match.start("elia");
		p1.setFiches(40);
		p2.setFiches(0);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_WON, match.gameWinner());
		
		p1.setFiches(40);
		p2.setFiches(40);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_DREW, match.gameWinner());
		
		p1.setFiches(0);
		p2.setFiches(40);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_lOST, match.gameWinner());	
	}
}
