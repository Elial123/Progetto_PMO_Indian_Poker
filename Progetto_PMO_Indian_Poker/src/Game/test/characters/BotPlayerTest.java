package Game.test.characters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Game.project.resources.model.characters.BotPlayer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.Card;
import Game.project.resources.model.enums.Suits;

import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;

class BotPlayerTest {

    private BotPlayer testedBot;
    private Player opponentTest1,
                   opponentTest2;
    private List<Player> listOfPlayer;

    @BeforeEach
    void setUp() {
        // Inizializzalizzazione 
        testedBot = new BotPlayer();
        
        opponentTest1 = new BotPlayer();
        opponentTest2 = new BotPlayer();
        
        // assegna nome al testedBot
        testedBot.setName("bot1");
        
        // Assegna carte, bid e nomi agli avversari
        opponentTest1.setHand(new Card(10, Suits.QUADRI), new Card(6, Suits.PICCHE));
        opponentTest1.setBid(2);
        opponentTest1.setName("bot2");
        
        opponentTest2.setHand(new Card(9, Suits.QUADRI), new Card(3, Suits.FIORI));
        opponentTest2.setBid(1);
        opponentTest2.setName("bot3");

        listOfPlayer = new ArrayList<>();
        listOfPlayer.add(testedBot);
        listOfPlayer.add(opponentTest1);
        listOfPlayer.add(opponentTest2);
    }
    
    // Test che controlla che il bot punti con coppia o semi
    @Test
    void testMakeMoveWithPairOrSuits() {
    	System.out.println("\n-------testMakeMoveWithPairOrSuits-----");
    	// Imposta la mano del bot in modo che abbia semi
        testedBot.setHand(new Card(10, Suits.FIORI), new Card(5, Suits.FIORI)); 

        testedBot.makeMove(listOfPlayer);
        
        // controlla se il bot effettua una mossa 
        assertTrue(testedBot.getBid() > 0);
        // controlla che il bot non punti più di quello che ha
        assertTrue(testedBot.getBid() <= testedBot.getFiches());
        
        // Imposta la mano del bot in modo che abbia una coppia
        testedBot.setHand(new Card(5, Suits.PICCHE), new Card(5, Suits.FIORI)); 
        
        testedBot.makeMove(listOfPlayer);
        
        // controlla se il bot effettua una mossa 
        assertTrue(testedBot.getBid() > 0);
        // controlla che il bot non punti più di quello che ha
        assertTrue(testedBot.getBid() <= testedBot.getFiches());
    }

    // Test che controlla che il bot punti con mano forte
    @Test
    void testMakeMoveWithHighFirstCard() {
    	System.out.println("\n-------testMakeMoveWithHighFirstCard-----");
    	
        // Imposta la mano del bot in modo che abbia una carta alta
        testedBot.setHand(new Card(10, Suits.CUORI), new Card(5, Suits.FIORI)); 
        
        testedBot.makeMove(listOfPlayer);
        
        // controlla se il bot effettua una mossa 
        assertTrue(testedBot.getBid() > 0);
        // controlla che il bot non punti più di quello che ha
        assertTrue(testedBot.getBid() <= testedBot.getFiches());
    }
    
    // Test che controlla che il bot punti basso o foldi con mano debole
    @Test
    void testMakeMoveWithLowFirstCard() {
    	System.out.println("\n-------testMakeMoveWithLowFirstCard-----");
    	
        // Imposta la mano del bot in modo che abbia una carta bassa
        testedBot.setHand(new Card(1, Suits.FIORI), new Card(5, Suits.CUORI)); 
        
        testedBot.makeMove(listOfPlayer);
        
        // verifica che il bot possa foldare o scommettere basso
        assertTrue(testedBot.getBid() <= 1);
    }

    // Test che controlla che il bot punta con mano forte ma bid più alte
    @Test
    void testMakeMoveAgainstOpponentsWithHigherBids() {
        // Imposta una mano forte
        testedBot.setHand(new Card(10, Suits.CUORI), new Card(5, Suits.FIORI)); 
        
        System.out.println("\n-------testMakeMoveAgainstOpponentsWithHigherBids-----");
        
        // setto avversari con più alte
        opponentTest1.setBid(5);
        System.out.println(opponentTest1.getHand().getSuitFirstCard());
        opponentTest2.setBid(3);
        System.out.println(opponentTest2);
        
        testedBot.makeMove(listOfPlayer);
        
        System.out.println(testedBot.getBid());
        // dovrebbe provare a puntare
        assertTrue(testedBot.getBid() > 0);
        // controlla che il bot non punti più di quello che ha
        assertTrue(testedBot.getBid() <= testedBot.getFiches());
    }
    
    // test caso mancanza di avversari
    @Test
    public void testMakeMoveWithNoOpponent() {
        List<Player> players = new ArrayList<>();
        players.add(testedBot); // Aggiungi il BotPlayer alla lista, senza avversari
        System.out.println("\n-------testMakeMoveWithNoOpponent-----");
        testedBot.makeMove(players);
        
        // dovrebbe foldare se non ci sono avversari
        assertTrue(testedBot.getFold());
    }
    
    // test caso in cui avversari foldino
    @Test
    public void testMakeMoveWhenOpponentFolds() {
    	// Imposta una mano forte
    	testedBot.setHand(new Card(10, Suits.CUORI), new Card(5, Suits.FIORI)); 
    	System.out.println("\n-------testMakeMoveWhenOpponentFolds-----");
    	// set fold degli avversari
    	opponentTest1.setFold(true); 
    	opponentTest1.setBid(1);
    	opponentTest2.setFold(true);

        List<Player> players = new ArrayList<>();
        players.add(testedBot);
        players.add(opponentTest1);
        players.add(opponentTest2);

        testedBot.makeMove(players);
        
        // non dovrebbe foldare ma puntare
        assertFalse(testedBot.getFold());
        assertTrue(testedBot.getBid() > 0);
    }
    
    // test che controlla che un bot sia in grado di bleffare
    @Test
    public void testBluffingBehavior() {
        testedBot.setHand(new Card(9, Suits.FIORI), new Card(2, Suits.FIORI)); // mano debole
        System.out.println("\n-------testBluffingBehavior-----");

        // avversario che ha una mano forte
        opponentTest1.setHand(new Card(10, Suits.CUORI), new Card(9, Suits.CUORI));
        opponentTest1.setBid(5);

        List<Player> players = new ArrayList<>();
        players.add(testedBot);
        players.add(opponentTest1);

        testedBot.makeMove(players);

        // potrebbe bluffare e quindi puntare, anche se la sua mano è debole.
        assertTrue(testedBot.getBid() >= 1); // verifica se il bot ha puntato
    }
}

