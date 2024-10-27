package Game.test.match;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.BotPlayer;
import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.HumanPlayer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.enums.Result;
import Game.project.resources.model.match.BidIncorrectException;
import Game.project.resources.model.match.Match;

class TestMatch {
	private Match match;
	private Dealer dealer;
	List<Player> list;
	private Player p1, 
				   p2, 
				   p3, 
				   p4;
	
	@BeforeEach
    public void setUp() {
       dealer = Dealer.getInstance();
       p1 = new HumanPlayer();
       p2 = new BotPlayer();
       p3 = new BotPlayer();
       p4 = new BotPlayer();
       list = new ArrayList<>();
       
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
		list = match.start("elia", true);
		Assert.assertEquals("elia", list.get(0).getName());
		Assert.assertEquals(true, list.get(1).getChoice());
		
	}
	// test metodo distribution() 
	@Test
	void testDistribution() {
		match.start("elia", false);
		match.distribution();
		Assert.assertNotNull(p1.getHand().getCards().get(0));
		Assert.assertNotNull(p1.getHand().getCards().get(1));
	}
	
	// test metodo situationPlayer() 
	@Test
	void testSituationPlayer() {
		match.start("elia", false);
		match.distribution();
		Assert.assertNotNull(match.situationPlayer());
	}
	
	// test metodo playRound()
	@Test
	void testPlayRound() throws BidIncorrectException{
		List<Player> list;
		
		match.start("elia", false);
		match.distribution();
		
		match.playRound(); // ho appena cominciato 
						   // e i bot devono aspettare
		list = match.situationPlayer();
		Assert.assertEquals(1, list.get(1).getBid());
		
		match.start("elia", false);
		match.distribution();
		match.playRound();
		match.bid(1);
		list = match.situationPlayer();
		Assert.assertNotEquals(0, list.get(1).getBid());
		
	}
	// test metodo bid(int b)
	@Test
	void testBid() throws BidIncorrectException {
		System.out.println("\n\ntestBid-------------------------------------\n");
		match.start("elia", false);
		match.distribution();
		match.playRound();
		match.bid(4);
		Assert.assertEquals(5, p1.getBid()); // la tassa 1 + 5 la bid
		Assert.assertNotNull(p2.getBid());
		
		assertThrows(BidIncorrectException.class, () -> {
			match.bid(5);
        });
		
		p1.setFiches(4);
		assertThrows(BidIncorrectException.class, () -> {
			match.bid(4);
        });
		
	}
	// test metodo roundWinner()
	@Test
	void testRoundWinner() throws BidIncorrectException {
		System.out.println("\n\ntestRoundWinner-------------------------------------\n");
		match.start("elia", false);
		match.distribution();
		match.playRound();
		match.bid(4);
		match.roundWinner();
		Assert.assertNotEquals(10, p1.getFiches());
		// controllo sul tutti foldano...
	}
	// test metodo roundOver(), casi base
	@Test
	void testRound() throws BidIncorrectException {
		System.out.println("\n\ntestRoundOver-------------------------------------\n");
		match.start("elia", false);
		match.distribution();
		match.playRound();
		match.bid(1);
		match.roundWinner();
		
		Assert.assertNotNull(match.roundOver());
		System.out.println("result: "+match.roundOver());
		
		p1.setFiches(0);
		Assert.assertEquals(Result.YOU_lOST, match.roundOver());
		System.out.println("result: "+match.roundOver());
		
		p1.setFiches(40);
		p2.setFiches(1);
		p3.setFiches(1);
		p4.setFiches(1);
		
		match.playRound();
		match.bid(1);
		
		match.roundWinner();
		System.out.println("*********result: "+" "+match.situationPlayer());
		Assert.assertEquals(Result.YOU_WON, match.roundOver());
		
	}
	
	// test metodo roundOver(), caso limite 1
	@Test
	void testRound1() throws BidIncorrectException {
		System.out.println("\n\ntestRoundOver-------------------------------------\n");
		match.start("elia", false);
		match.distribution();
		match.playRound();
		match.bid(1);
		match.roundWinner();
		
		p1.setFiches(40);
		p2.setFiches(20);
		p3.setFiches(1);
		p4.setFiches(1);
		
		match.playRound();
		match.bid(1);
		
		match.roundWinner();
		System.out.println("*********result: "+" "+match.situationPlayer());
		Assert.assertEquals(Result.CONTINUE, match.roundOver());
		
	}
	
	// test metodo roundOver(), caso limite 2
	@Test
	void testRound2() throws BidIncorrectException {
		System.out.println("\n\ntestRoundOver-------------------------------------\n");
		match.start("elia", true);
		match.distribution();
		match.playRound();
		match.bid(1);
		match.roundWinner();
		
		p1.setFiches(20);
		p2.setFiches(20);
		p3.setFiches(1);
		p4.setFiches(1);
		
		match.playRound();
		match.bid(1);
		
		match.roundWinner();
		System.out.println("*********result: "+" "+match.situationPlayer());
		Assert.assertEquals(Result.YOU_WON, match.roundOver());
		
	}
	
	// test metodo roundOver(), caso limite 3
	@Test
	void testRound3() throws BidIncorrectException {
		System.out.println("\n\ntestRoundOver-------------------------------------\n");
		match.start("elia", true);
		match.distribution();
		match.playRound();
		match.bid(1);
		match.roundWinner();
		
		p1.setFiches(20);
		p2.setFiches(1);
		p3.setFiches(20);
		p4.setFiches(1);
		
		match.playRound();
		match.bid(1);
		
		match.roundWinner();
		System.out.println("*********result: "+" "+match.situationPlayer());
		Assert.assertEquals(Result.CONTINUE, match.roundOver());
		
	}
	// test metodo gameWinner()
	@Test
	void testGameWinner() throws BidIncorrectException {
		match.start("elia", false);
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
		
		match.start("elia", true);
		
		p1.setFiches(40);
		p2.setFiches(0);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_WON, match.gameWinner());
		
		p1.setFiches(40);
		p2.setFiches(40);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_WON, match.gameWinner());
		
		p1.setFiches(0);
		p2.setFiches(40);
		p3.setFiches(0);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_WON, match.gameWinner());	
		
		p1.setFiches(0);
		p2.setFiches(0);
		p3.setFiches(40);
		p4.setFiches(0);
		Assert.assertEquals(Result.YOU_lOST, match.gameWinner());
		
		p1.setFiches(0);
		p2.setFiches(0);
		p3.setFiches(0);
		p4.setFiches(40);
		Assert.assertEquals(Result.YOU_lOST, match.gameWinner());
		
		p1.setFiches(0);
		p2.setFiches(0);
		p3.setFiches(40);
		p4.setFiches(40);
		Assert.assertEquals(Result.YOU_lOST, match.gameWinner());
		
		p1.setFiches(40);
		p2.setFiches(0);
		p3.setFiches(40);
		p4.setFiches(40);
		Assert.assertEquals(Result.YOU_DREW, match.gameWinner());
		
		p1.setFiches(0); //**
		p2.setFiches(40);
		p3.setFiches(40);
		p4.setFiches(40);
		Assert.assertEquals(Result.YOU_DREW, match.gameWinner());
		
		p1.setFiches(40);
		p2.setFiches(40);
		p3.setFiches(40);
		p4.setFiches(40);
		Assert.assertEquals(Result.YOU_DREW, match.gameWinner());
	}
}
