package Game.project.resources.model.match;

import Game.project.resources.model.enums.Result;
import java.util.ArrayList;

import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.GameTimer;

public class Match implements IMatch{
	/* compongo n persone che implementano IPlayer */
    private ArrayList<Player> characters;
	private GameTimer timer;
	private int rounds;
	private int index; // salva chi ha iniziato il turno prec
	private Dealer dealer;  

	/* costruttore della classe */
	public Match(final Dealer dealer, final Player p1, final Player p2, final Player p3, final Player p4) {
		this.characters = new ArrayList<Player>();
		this.timer = new GameTimer();
		this.characters.add(p1);
		this.characters.add(p2);
		this.characters.add(p3);
		this.characters.add(p4);
		this.index = 0;	// comincia lo user
		this.dealer = dealer;
		this.rounds = 10;
	}
	
	@Override
	public void start(String name) {
		// assegnazione nomi
		this.characters.get(0).setName(name);
		this.characters.get(1).setName("bot1");
		this.characters.get(2).setName("bot2");
		this.characters.get(3).setName("bot3");
		//System.out.println(this.characters);
	}
	@Override
	public GameTimer getTimer() {
		return timer;
	}
	@Override
	public void distribution() {
		// non distribire a chi ha perso
		for(int i = 0; i < this.characters.size(); i++) {
			System.out.println("\ncarte di "+this.characters.get(i).getName()+": ");
			this.characters.get(i).setFirstCard(dealer.distribute());
			this.characters.get(i).setSecondCard(dealer.distribute());
		}
		System.out.println(this.characters);	
	}
	@Override
    public String situationPlayer() {
    	String str = new String();
    	
    	str = this.characters.get(0).toString()+", "+this.characters.get(0).getSecondCard();
    	for(int i = 1; i < this.characters.size(); i++)
    		str = str+this.characters.get(i);
		return str;
	}
	@Override
	public void payTaxes() {
		for(int i = 0; i < this.characters.size(); i++) {
			System.out.println("\n"+this.characters.get(i).getName()+" paga tassa...");
			this.characters.get(i).setBid(1);
		}
	}
	@Override
	public void fold(int index) {
		this.characters.get(index).setFold(true);
		System.out.println("\n"+this.characters.get(index).getName()+" passa...");
	}
	
	@Override
    public void bid(int b) throws BidIncorrectException{ // in caso di errore serve il throw (che dovrai implementare)
		int bidOfPlayer = 0;
		int count = 0,
			i;
		
		this.payTaxes();
		
		for(int c = 0; c < this.characters.size(); c++) // resetta il fold
    		this.characters.get(c).setFold(false);
		
		i = index;
		while(count < this.characters.size()) {
			count = count +1;
			
			// distingui bot da user
			if(i == 0) {
				bidOfPlayer = b; // bid dello user
				
				if(this.characters.get(i).getFiches() >= 2) { // se puoi pagare
					if(b > 4 || b < 0) {
						this.characters.get(i).setBid(this.characters.get(i).getBid() + 0);
						this.fold(0);
						throw new BidIncorrectException();
					} else {
						if(this.characters.get(0).getFiches() < b + 1) { // bid + tassa < fiches
							this.characters.get(i).setBid(this.characters.get(i).getBid() + 0);
							this.fold(0);
							throw new BidIncorrectException();
						} else {
							this.characters.get(i).setBid(this.characters.get(i).getBid() + b); // [1, 5]
						}
					}
			
				} else {
					// non hai fiches sufficenti per continuare
					this.characters.get(i).setBid(0);
				}

				if(bidOfPlayer == 0)
					this.fold(0);
			} else {
				
				if(this.characters.get(i).getFiches() >= 2) { // se hai ancora sufficenti fiches
					if(this.characters.get(i).getFiches() >= 5) {
						bidOfPlayer = (int)(Math.random() * (4 - 0)) + 0; // [0, 4] 
						this.characters.get(i).setBid(this.characters.get(i).getBid() + bidOfPlayer); // [1, 5] o [firstBid, 5]
					} else {
					    bidOfPlayer = (int)(Math.random() * ((this.characters.get(i).getFiches()-1) - 0)) + 0; // [0, fiches - 1]
						this.characters.get(i).setBid(this.characters.get(i).getBid() + bidOfPlayer);// [0, fiches]
					}
					
					if(bidOfPlayer == 0) {
						this.fold(i);
					}
				} else {
					this.characters.get(i).setBid(0); // sei fuori dalla partita
					this.fold(i); 
					// hai perso
					
				}
				
			}

			
			System.out.println(this.characters.get(i).getName()+" ha puntato: "+
							   this.characters.get(i).getBid());
			if(i < this.characters.size() - 1)
				i++;
			else
				i = 0;
		}
		// cambia il player che punta per primo
		if(this.index < this.characters.size() - 1)
			index++;
		else
			index = 0;
		
		System.out.println(this.characters);
    }
	@Override
    public Result roundOver() {
    	
    	if(this.characters.get(0).getFiches() <= 1) // se user non ha più fiches
    		return Result.YOU_lOST;
    	if(this.characters.size() == 1) // se user è rimasto solo in partita
    		return Result.YOU_WON;
    	
    	this.rounds--;
    	System.out.println("\nrounds rimanenti: "+this.rounds);
    	
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
		
		//for(int i = 0; i < this.characters.size(); i++) // resetta il fold
    		//this.characters.get(i).setFold(false);
		
    }
	@Override
    public Result gameWinner() {	
    	
    	int winnerID = 0, 
    		drewerID = 0, 
    		fichesWinner = this.characters.get(0).getFiches();
    	
    	// ricerca vincitore 
    	for(int i = 0; i < this.characters.size() - 1; i++) {	// da 0 a 2
			if(fichesWinner < this.characters.get(i+1).getFiches())
			{
				//System.out.println("-----fiches: "+fichesWinner);
				fichesWinner = this.characters.get(i+1).getFiches();
				winnerID = i+1;
				//System.out.println("-----vincitore: "+winnerID);
			}
			
	     }
    	
    	System.out.println("viches vincitore: "+fichesWinner);
    	System.out.println("numero vincitore: "+winnerID);
    	
    	// ricerca eventuale pareggio
    	if (winnerID == 0)
    		for(int i = 1; i < this.characters.size(); i++) {	
				if(fichesWinner == this.characters.get(i).getFiches()) {
					drewerID = i;
				}	
		     }
    	// restituzione risultato
    	if(winnerID == 0) {
    		if(drewerID != 0) {
    			return Result.YOU_DREW;
    		}
    		else {
    			return Result.YOU_WON;
    		}
    	} else 
    		return Result.YOU_lOST;
    		
    }
	

}