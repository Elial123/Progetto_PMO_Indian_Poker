package Game.project.resources.model.match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.GameStopwatch;
import Game.project.resources.model.enums.Result;

public class Match implements IMatch {
	/* compongo n persone che implementano IPlayer */
    private ArrayList<Player> characters,
    						  supportList;
    
    private boolean pairMode,
    				hasBet;
	private GameStopwatch gameStopwatch;
	private int rounds;
	private int index; // salva chi ha iniziato il turno prec
	private Dealer dealer; 

	/* costruttore della classe */
	public Match(final Dealer dealer, final Player p1, final Player p2, final Player p3, final Player p4) {
		this.characters = new ArrayList<Player>();
		this.gameStopwatch = new GameStopwatch();
		this.characters.add(p1);
		this.characters.add(p2);
		this.characters.add(p3);
		this.characters.add(p4);
		
		this.supportList = new ArrayList<Player>(this.characters);
		
		this.index = 0;	// comincia lo user
		this.dealer = dealer;
		this.pairMode = false;
		this.hasBet = false;
		this.rounds = 10;
	}
	
	@Override
	public ArrayList<Player> start(String name, boolean choice) {
		this.pairMode = choice;
		// assegnazione nomi
		this.characters.get(0).setName(name);
		this.characters.get(0).setChoice(choice);
		
		this.characters.get(1).setName("bot1");
		this.characters.get(1).setChoice(choice);
		
		this.characters.get(2).setName("bot2");
		this.characters.get(2).setChoice(choice);
		
		this.characters.get(3).setName("bot3");
		this.characters.get(3).setChoice(choice); 
		//System.out.println(this.characters);
		ArrayList<Player> list = this.characters;
    	return list;
	}
	
	@Override
	public GameStopwatch getTimer() {
		return gameStopwatch;
	}
	
	@Override
	public int getTurn() {
		return this.index;
	}
	
	@Override
	public void distribution() {
		
		// non distribire a chi ha perso
		for(int i = 0; i < this.characters.size(); i++) {
			this.characters.get(i).setHand(this.dealer.distribute(), this.dealer.distribute());
			System.out.println("\ncarte di "+this.characters.get(i).getName()+": "+
					this.characters.get(i).getHand());
		}
		System.out.println(this.characters);	
	}
	
	@Override
    public ArrayList<Player> situationPlayer() {
		// dovrà restituire una lista di 4 player
		// dovra valutare se non sei tu ha puntare per primo e
		// aggiornare le bid dei player che hanno il turno prima del tuo
    	String str = new String();
    	
    	str = this.characters.get(0).toString()+" carte"+this.characters.get(0).getHand()+", ";
    	for(int i = 1; i < this.characters.size(); i++)
    		str = str+this.characters.get(i)+" carte"+this.characters.get(i).getHand()+", ";
		//return str;
    	ArrayList<Player> list = this.characters;
    	System.out.println(str);
    	return list;
	}
	
	private void payTaxes() {
		for(int i = 0; i < this.characters.size(); i++) {
			System.out.println("\n"+this.characters.get(i).getName()+" paga tassa...");
			this.characters.get(i).setBid(1);
		}
	}
	
	private void fold(int index) {
		this.characters.get(index).setFold(true);
		System.out.println("\n"+this.characters.get(index).getName()+" passa...");
	}
	
	@Override
	public void playRound() {
		
		this.hasBet = false;
		
		this.payTaxes();

		// Resetta il fold per ogni personaggio
		for (int c = 0; c < this.characters.size(); c++) {
		    this.characters.get(c).setFold(false);
		}
		
		/*for(int i = 0 ; i < this.characters.size(); i++)
    		this.characters.get(i).setBid(0);*/
		
		if(index > 0) {
			for(int i = index; i<this.characters.size(); i++)
				if (this.characters.get(i).getFiches() >= 2)
					this.characters.get(i).makeMove(characters);
				else {
		    		this.characters.get(i).setBid(0); // sei fuori dalla partita
					this.fold(i); 
					// hai perso
		    	}
		}
		
	}
	
	@Override
    public void bid(int b) throws BidIncorrectException{ // in caso di errore serve il throw (che dovrai implementare)
		int bidOfPlayer = 0;
		int count = 0;

		int i = index;
		while (count < this.characters.size()) {
		    // Incrementa il conteggio
		    count++;
		    
		    // Distingui bot da user
		    if (i == 0) {
		        bidOfPlayer = b; // Bid dello user

		        // Verifica se il giocatore ha fiches sufficienti
		        if(this.hasBet)
		        	throw new BidIncorrectException("Bid già inserita!");
		        else if (this.characters.get(i).getFiches() >= 2) {
		            // Controlla se il bid è valido
		            if (b > 4 || b < 0) {
		                this.characters.get(i).setBid(+this.characters.get(i).getBid());
		                this.fold(0);
		                this.hasBet = false;
		                throw new BidIncorrectException("Il numero inserito non è tra 0 e 4!");
		            } else if (this.characters.get(i).getFiches() < b + 1) { 
		                this.characters.get(i).setBid(+this.characters.get(i).getBid());
		                this.fold(0);
		                this.hasBet = false;
		                throw new BidIncorrectException("Il numero inserito è più grande del numero delle tue fiches!");
		            } else {
		            	this.hasBet = true;
		                this.characters.get(i).setBid(b+this.characters.get(i).getBid()); // add tassa
		            }
		        } else {
		            // Non hai fiches sufficienti per continuare
		            this.characters.get(i).setBid(0);
		        }

		        // Gestisci il fold in base al bid
		        if (bidOfPlayer == 0) {
		            this.fold(0);
		        }

		        System.out.println(this.characters.get(i).getName() + " ha puntato: " +
		                           this.characters.get(i).getBid());
		    } else {
		    	if(this.characters.get(i).getFiches() >= 2) {
		    		if(this.characters.get(i).getBid() == 1 && !this.characters.get(i).getFold()) // se non ha ancora puntato
		    			this.characters.get(i).makeMove(characters); // Logica per la puntata del bot
		    	} else {
		    		this.characters.get(i).setBid(0); // sei fuori dalla partita
					this.fold(i); 
					// hai perso
		    	}
		    }


		    // Aggiorna l'indice per il prossimo giocatore
		    i = (i + 1) % this.characters.size(); // Ciclo tra i personaggi
		}

		// Cambia il player che punta per primo
		index = (index + 1) % this.characters.size();
		
		
		

    }
	
	@Override
    public Result roundOver() {
    	
    	if(this.characters.get(0).getFiches() <= 1) // se user non ha più fiches
    		return Result.YOU_lOST;
    	if(this.characters.size() == 1 || // se user è rimasto solo in partita
    	   (this.characters.size() == 2 && 
    	   this.characters.get(1).getName().equals("bot1") && 
    	   this.pairMode)) 				  // o user e bot1 restano soli
    		return Result.YOU_WON;
    	
    	this.rounds--;
    	System.out.println("\nrounds rimanenti: "+this.rounds);
    	
    	//this.dealer.r
    	this.dealer.shuffle(); // rimescola le carte
    	return Result.CONTINUE;
    }
	
	@Override
    public void roundWinner() {
    	int winnerNumber;
    	int fichesWon;
    	
		winnerNumber = this.dealer.winner(this.characters);
		
		if(winnerNumber == -1) { // foldano tutti
			System.out.println("tutti foldano");		
		} else {
			fichesWon = 0;
			for(int i = 0; i < this.characters.size(); i++) {
				if(i != winnerNumber) {
					fichesWon = fichesWon + this.characters.get(i).getBid();
				}
			}
			System.out.println("vincitore: "+this.characters.get(winnerNumber).toString()+",\n fichesWon: "+fichesWon);
			this.dealer.calculateNumberOfFiches(this.characters, winnerNumber, fichesWon);
		}
		
		// se hai 0 fiches pop
		for(int i = this.characters.size()-1; i >= 0; i--) { // rimozione perdenti
			if(this.characters.get(i).getFiches() <= 1) {
				if (i == 0)
					System.out.println("hai perso!");//this.roundOver();
				else
				    this.characters.remove(i);
			    index = 0; // ricomincio il giro
			}
		}
    }
	
	@Override
    public Result gameWinner() {
    	
    	List<Player> winners = new ArrayList<>();
    	 // Trova il giocatore con il maggior numero di fiches
        Optional<Player> winner = this.characters.stream()
        		.max(Comparator.comparingInt(Player::getFiches));

        if (winner.isPresent()) { // se vincitore è presente cerca casi di pareggio
            winners = this.characters.stream()
            		.filter(p -> p.getName() != winner.get().getName())
            		.filter(p -> p.getFiches() == winner.get().getFiches())
                    .toList();
        } else {
            System.out.println("Non ci sono giocatori.");
        }
        
        if(!this.pairMode) { // controllo mod
    		if(winner.get().getName() == this.supportList.get(0).getName()) { // conttrollo vincitore
        		if(winners.size() != 0) { // controllo pareggio
        			return Result.YOU_DREW;
        		}
        		else {
        			return Result.YOU_WON;
        		}
        	} else 
        		return Result.YOU_lOST;
    	} else { // confronto nome vincitore con i due partner
    		if(winner.get().getName() == this.supportList.get(0).getName() ||
    		   winner.get().getName() == this.supportList.get(1).getName()) {
    			
    			if(winners.contains(this.supportList.get(2)) || 
    			   winners.contains(this.supportList.get(3))) { // controllo presenza di paregio
    				
        			return Result.YOU_DREW;
        		}
        		else {
        			return Result.YOU_WON;
        		}
    		} else 
        		return Result.YOU_lOST;	
    	}
        
    	// Nota: per vincere alla fine non basta che il partner vinca, ma devi anche rimanere in partita
    	// se non hai le fiches per rimanere in partita allora non puoi vincere
    	// qui vale lo stesso discorso
    }
}