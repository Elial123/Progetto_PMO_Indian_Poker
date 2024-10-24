package Game.project.resources.model.characters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Counter;
import Game.project.resources.model.elements.Deck;

public class Dealer implements IDealer{
	// attributi interni
	private static Dealer dealer;
	private String name;
	private Deck deck;
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
		return this.deck.getCard();
	}
	
	@Override
	public int winner(ArrayList<Player> characters) {
		Optional<Player> winnerPlayer = characters.stream()
                .filter(player -> !player.getFold())
                .max(Comparator.comparingInt(Player::combinationType)
                		.thenComparingInt(player -> player.getHand().getRankFirstCard()) // Confronta solo il valore della prima carta
                        .thenComparingInt(player -> player.getHand().getSuitFirstCard().getPriority()) // Confronta solo il seme della prima carta
                );

        return winnerPlayer.map(characters::indexOf).orElse(-1);
	}
	 
	@Override
	public void calculateNumberOfFiches(ArrayList<Player> characters, int winnerNumber, int fichesWon) {
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
		return "John";
	}

}