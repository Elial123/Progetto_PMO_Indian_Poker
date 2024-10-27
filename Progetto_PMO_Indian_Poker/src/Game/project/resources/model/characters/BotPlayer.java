package Game.project.resources.model.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Game.project.resources.model.elements.Card;
import Game.project.resources.model.elements.Hand;

public class BotPlayer extends Player {
	
	private static final double BLUFFING_COEFFICIENT = 0.3 + Math.random() * (1 - 0.3);
	private static final int LIMIT_MAX = 5;
	private static final int LIMIT_MIN = 2;
	private static final int BET_LIMIT = 4;
	private static final Random random = new Random();

    public BotPlayer() {
        super();
    }

    @Override
    public void makeMove(List<Player> players) {// arg in PokerDecision
    	
    	Hand playerHand,
    		 partnerHand = null; // inizializza a null;
    	List<Pair<Card, Integer>> opponentsInfo = new ArrayList<>();
		boolean pairChoice,
		        partnerHasFolded = false;
		  
		this.setFold(false); // resetta il fold
		  
		playerHand = this.getHand();
		  
		pairChoice = players.get(0).getChoice();
		  
		
		if(this.getName().equals("bot1")) {
			partnerHand = players.get(0).getHand();
			partnerHasFolded = players.get(0).getFold();
		} else if(this.getName().equals("bot2") && players.get(players.size()-1).getName().equals("bot3")) {
			partnerHand = players.get(players.size()-1).getHand();
			partnerHasFolded = players.get(players.size()-1).getFold();
		} else if(this.getName().equals("bot3") && players.get(players.size()-2).getName().equals("bot2")) {
			partnerHand = players.get(players.size()-2).getHand();
			partnerHasFolded = players.get(players.size()-2).getFold();
		}
		
		for(int i = 0; i < players.size(); i++) {
			if(this.getName() != players.get(i).getName())
				opponentsInfo.add(new Pair<Card, Integer>(players.get(i).getHand().getCards().get(0), players.get(i).getBid()));
		}
		
		if (this.shouldPlay(playerHand, 
				            partnerHand, 
	  			            opponentsInfo, 
	  			            pairChoice,
	  			            partnerHasFolded)) {
			
			raise(this.shouldRaise(playerHand, 
					               opponentsInfo, 
					               pairChoice, 
					               partnerHasFolded));
			System.out.println(" entra");
	    } else {
	    	this.fold();
	    	System.out.println(this.getName()+" ha foldato");
	    }
	    		
	}
	    
    private void fold() {
    	this.setFold(true);
			
	}

    private void raise(int muchRaise) {
        // Controlla se il giocatore ha abbastanza fiches
        if(this.getFiches() >= muchRaise) {
            // nuova puntata (tassa + scelta del bot)
            int newBid = this.getBid() + muchRaise;
            
            // controllla che la nuova puntata sia tra LIMIT_MIN e LIMIT_MAX
            if (newBid > LIMIT_MAX) {
                newBid = LIMIT_MAX; // se supera il massimo, imposta a LIMIT_MAX
            } else if (newBid < LIMIT_MIN) {
                newBid = LIMIT_MIN; // se è inferiore al minimo, imposta a LIMIT_MIN
            }
            
            // imposta la nuova puntata
            this.setBid(newBid);
        } else {
            // se il giocatore non ha abbastanza fiches, assegna le fiches disponibili
            this.setBid(Math.max(this.getFiches(), LIMIT_MIN)); // si assicura che sia almeno LIMIT_MIN
        }

        // stampa il nome e l'importo della puntata
        System.out.println(this.getName() + " ha puntato: " + this.getBid());
    }

	private int shouldRaise(
	        Hand playerHand, 
	        List<Pair<Card, Integer>> opponentsInfo, 
	        boolean pairChoice,
	        boolean partnerHasFolded) {

	    // puntata se ho un maiale
	    if (this.combinationType() == 1) {

	        // Controlla se altri giocatori hanno una carta con lo stesso valore e se il giocatore ha una carta abbastanza alta 
	        if ((opponentsInfo.stream().filter(pair -> pair.getCard().getRank() != (playerHand.getRankFirstCard())).count() > 0) 
	                && playerHand.getRankFirstCard() >= BLUFFING_COEFFICIENT * 10) {

	            if (BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.6)) {
	                return (int) ((BET_LIMIT * BLUFFING_COEFFICIENT));
	            } else if (playerHand.getRankFirstCard() < 9) {
	                return (int) (playerHand.getRankFirstCard() / 2 + (BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.3) ? 1 : 0));
	            } else {
	                return (random.nextInt(2) + 1); // nextInt(2) restituisce 0 o 1, +1 per avere 1 o 2
	            }
	        }

	        // controlla se altri giocatori hanno una carta dello stesso seme e se vuole rischiare un po'
	        if ((opponentsInfo.stream().filter(pair -> pair.getCard().getSuit() != (playerHand.getSuitFirstCard())).count() > 0) 
	                && BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.3)) {

	            if (playerHand.getRankFirstCard() >= BLUFFING_COEFFICIENT * 10) {
	                return (int) (playerHand.getRankFirstCard() / 2.3 + (BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.3) ? 1 : 0));
	            } else {
	                return (random.nextInt(2) + 1);
	            }
	        }

	        // controlla se altri giocatori non hanno una carta più alta del player e se vuole rischiare un po'
	        if (opponentsInfo.stream().filter(pair -> pair.getCard().getRank() > playerHand.getRankFirstCard()).count() > 0 
	                && BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.3)) {
	            return (int) (playerHand.getRankFirstCard() / 2.5 + (BLUFFING_COEFFICIENT >= Math.random() * (1 - 0.3) ? 1 : 0));
	        }

	        // il bot sceglie se bluffare
	        if (Math.random() * (1 - 0.3) >= BLUFFING_COEFFICIENT) {
	            return (int) (BET_LIMIT * (Math.random() * (1 - 0.5)));
	        }

	        return (1);
	    }

	    // puntata se ho semi (la logica è molto più semplice perchè con semi si ha una vittoria probabile)
	    else if (this.combinationType() == 2) {
	        if (this.getHand().getRankFirstCard() > BLUFFING_COEFFICIENT * 10) {
	            return (int) (Math.random() * (BET_LIMIT + BLUFFING_COEFFICIENT - 2) + 2);
	        }
	        return (int) (Math.random() * ((3 + BLUFFING_COEFFICIENT) - 2) + 2);
	    }

	    // puntata se ho coppia (la logica è quasi assente perchè la vittoria è estremamente probabile)
	    else {
	        return (int) (Math.random() * ((BET_LIMIT + BLUFFING_COEFFICIENT) - 2) + 2);
	    }
	}

		

	private boolean shouldPlay(Hand playerHand,
							   Hand partnerHand, 
		                       List<Pair<Card, Integer>> opponentsInfo, 
			                   boolean pairChoice,
			                   boolean partnerHasFolded) {
		
		boolean hasHigherRank = true,
			    hasSameRank = true,
			    sameRankButHigherSuit,
			    hasOpponentRaised,
			    riseUnderTheLimit,
			    decisionCondition;

	    // Controlla se ci sono altri giocatori in gara
	    if (opponentsInfo.size() == 0)
	    	return false;

		// Controllo inserimento corretto di partner
		if (partnerHand == null)
			pairChoice = false;

		// Controlla se il giocatore ha una o più carte dello stesso seme
		if (playerHand.hasPair() || playerHand.hasSameSuit()) {
			return true; // Se sì -> punta!
		}

		// Valutazione se il rank della prima carta dell'avversario è più grande
		hasHigherRank = opponentsInfo.stream()
				.noneMatch(pair -> pair.getCard().getRank() > playerHand.getRankFirstCard());

		hasSameRank = opponentsInfo.stream()
			    .anyMatch(pair -> pair.getCard().getRank() == playerHand.getRankFirstCard());

		if (hasSameRank)
			hasHigherRank = false;

		System.out.println("hasHigherRank: " + hasHigherRank);
		System.out.println("hasSameRank: " + hasSameRank);

		// Valuta le puntate degli avversari - se qualcuno ha puntato
		hasOpponentRaised = opponentsInfo.stream().anyMatch(pair -> pair.getBet() > 1);
		System.out.println("hasOpponentRaised: " + hasOpponentRaised);
		if(hasOpponentRaised)
		System.out.println(opponentsInfo.stream().filter(pair -> pair.getBet() > 1).findFirst().get().getCard());

		// Logica per foldare se ci sono puntate significative da parte degli avversari
		riseUnderTheLimit = opponentsInfo.stream()
				.max((p1, p2) -> p1.getBet().compareTo(p2.getBet()))
			    .get()
			    .getBet() < LIMIT_MAX; // limite di puntata

		System.out.println("riseUnderTheLimit: " + riseUnderTheLimit);

		// Valuta se coloro che hanno lo stesso rango hanno seme di maggiore priorità
		sameRankButHigherSuit = opponentsInfo.stream()
				.filter(pair -> pair.getCard().getRank() == playerHand.getRankFirstCard())
			    .anyMatch(p -> p.getCard().getSuit().getPriority() > playerHand.getSuitFirstCard().getPriority());
		System.out.println("sameRankButHigherSuit: " + sameRankButHigherSuit);

		if (sameRankButHigherSuit && hasOpponentRaised) {
		    decisionCondition = true;
		} else {
		    decisionCondition = riseUnderTheLimit;
		}
		//decisionCondition = riseUnderTheLimit && sameRankButHigherSuit;

		System.out.println("decisionCondition: " + decisionCondition);

		// Decisione
		if (hasHigherRank) {
			System.out.println("\nresult1: " + hasHigherRank);
			return true;
		} else { // se hanno stesso rank o minore
			if (hasSameRank) {
				if (pairChoice == false) {
					System.out.println("\nresult2: " + (decisionCondition));
			        return decisionCondition;
			    } else {
			    	System.out.println("\nresult3: " + (partnerHand.hasPair() || partnerHand.hasSameSuit()));
			        if (partnerHand.hasPair() || partnerHand.hasSameSuit()) {
			        	System.out.println("\nresult4: " + partnerHasFolded);
			        	if(this.getFiches() > BET_LIMIT)
			        		return partnerHasFolded ? decisionCondition : true;
			        	else
			        		return decisionCondition;
			        } else {
			            return decisionCondition;
			        }
			    }
			} else {
				return false;
			}
		} // end if
	}// end metodo
}
