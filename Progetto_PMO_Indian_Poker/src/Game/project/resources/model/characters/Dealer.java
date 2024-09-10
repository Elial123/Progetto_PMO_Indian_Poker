package Game.project.resources.model.characters;

import java.util.ArrayList;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Counter;
import Game.project.resources.model.elements.Deck;

public class Dealer extends Person implements IDealer{
	// attributi interni
	private static Dealer dealer;
	private String name;
	private Deck deck;
	private Card distributedCard;
	private Counter counter;
	
	// costruttore
	private Dealer() {
		this.name = "Dealer";
		this.deck = new Deck();
		this.counter = new Counter();
	}
	
	public static Dealer getInstance() {
		if(dealer == null) {
			dealer = new Dealer();
		}
		return dealer;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Card distribute() {
		this.distributedCard = deck.getCard();
		System.out.println(this.distributedCard);
		return this.distributedCard;
	}
	@Override
	public int winner(ArrayList<Player> characters) {
		int winnerNumber = -1;
		Player tempPlayerWinner = new Player();
		//Player tempPlayer = new Player();
		int maxCardValue = 0;
		
		for(int i = 0; i < characters.size(); i++) {
			System.out.println("------------------"+characters.get(i).getFold());
			if(!characters.get(i).getFold()) {
				if(winnerNumber == -1) {
					winnerNumber = i;
					tempPlayerWinner = characters.get(i);
					maxCardValue = tempPlayerWinner.getFirstCard().getValue();
				} 
				if (tempPlayerWinner.combinationType() > characters.get(i).combinationType()) { // controllo combinazione migliore
					winnerNumber = i;
					tempPlayerWinner = characters.get(i);
					maxCardValue = tempPlayerWinner.getFirstCard().getValue();
				} else {
					if (tempPlayerWinner.combinationType() == characters.get(i).combinationType())	// se stessa combinazione
						if (maxCardValue < (characters.get(i).getFirstCard().getValue())) { // controllo prima carta max
							winnerNumber = i;
							tempPlayerWinner = characters.get(i);
							maxCardValue = tempPlayerWinner.getFirstCard().getValue();
					    } else {
					    	if (maxCardValue == (characters.get(i).getFirstCard().getValue())) // se stessa prima carta
					    		if(tempPlayerWinner.getFirstCard().getSeed() < characters.get(i).getFirstCard().getSeed()) { // controllo seme prima carta
					    			winnerNumber = i; // testare
					    			tempPlayerWinner = characters.get(i);
					    			maxCardValue = tempPlayerWinner.getFirstCard().getValue();
					    		}
					    }
				}
			}
		}	
		
		return winnerNumber;
	}
	@Override
	public void calculateNumberOfFiches(ArrayList<Player> characters, int winnerNumber, int fichesWon) {
		//characters.get(winnerNumber);
		System.out.println("\nfiches vinte: "+fichesWon);
		characters.get(winnerNumber).setFiches(this.counter.inc(characters.get(winnerNumber).getFiches(), fichesWon)); // calcolo fiches vincitore e assegno
		
		for(int i = 0; i < characters.size(); i++) {
			if (i != winnerNumber) {
				characters.get(i).setFiches(this.counter.dec(characters.get(i).getFiches(),
						                                     characters.get(i).getBid()));
			}
		}
	}
	@Override
	public void shuffle() {
		this.deck.reEnteringCards();
	}
	
	@Override
	public String toString() {
		return "Dealer";
	}

}
