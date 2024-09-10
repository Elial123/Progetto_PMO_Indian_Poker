package Game.project.resources.controller;

import Game.project.resources.model.match.BidIncorrectException;
import Game.project.resources.model.match.Match;
import Game.project.resources.view.IndianPokerViewObserver;
import Game.project.resources.view.IndianPokerViewImpl;
import Game.project.resources.model.elements.GameTimer;
import Game.project.resources.model.enums.Result;
import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.Player;

public class IndianPokerApp implements IndianPokerViewObserver{
	
	private final Match model;
	private final IndianPokerViewImpl view;
	
	private final GameTimer gameTimer; 
	private String playerName;
    private int clickCount;
    private int precTime;
    private Result result;
	
	public IndianPokerApp() {
		this.clickCount = 0;
		this.precTime = 150;
		this.model = new Match(Dealer.getInstance(), new Player(), new Player(), new Player(), new Player()); 
		this.view = new IndianPokerViewImpl();
		this.view.setObserver(this);
		this.view.start();
		this.gameTimer = model.getTimer();
		
	}
	
	@Override
	public void play() {
	    gameTimer.start();
	    this.model.start(playerName);
	    this.model.distribution();
	    
	    while(gameTimer.getRemainingTime() >= 0) {
	    	
	    	
			if (gameTimer.getRemainingTime() == 150) // separato per poter permettere inserimento di testo
													 // riguardante la scelta effettuata dall'utente
													 // nel secondo pannello
				this.view.setText(this.model.situationPlayer());
			if (gameTimer.getRemainingTime() == 135 || gameTimer.getRemainingTime() == 120
					|| gameTimer.getRemainingTime() == 105 || gameTimer.getRemainingTime() == 90
					|| gameTimer.getRemainingTime() == 75 || gameTimer.getRemainingTime() == 60
					|| gameTimer.getRemainingTime() == 45 || gameTimer.getRemainingTime() == 30
					|| gameTimer.getRemainingTime() == 15 ) // 9 round
			{
				this.view.setText(this.game(gameTimer.getRemainingTime()));
				this.view.setCount("Fine turno");
			}
			else {
				if (gameTimer.getRemainingTime() == 0) { // 10cimo round
			    	this.view.setCount("Fine partita");
					this.view.setText(this.game(0));
		    	} else 
		    		this.view.setCount("Tempo rimanente: "+gameTimer.getRemainingTime());
			}
			
		}
		
	}
	
	private String game(int time) {
		
		if (time != this.precTime) {
			//this.model.payTaxes(); // pagano tasse
			try {
				if (this.clickCount == 0) // paga tassa se non scegle
					this.model.bid(0);
				else 
					this.clickCount = 0;
			} catch (BidIncorrectException e) {
				System.out.println("\n numero inserito non corretto");
				this.view.numberIncorrect();
			}
			
			this.model.roundWinner();
			result = this.model.roundOver();
			if(result != Result.CONTINUE) {
				this.view.result(result);// passo il risultato alla view
				this.quit();
			}
				
			System.out.println("time: "+time);
			if(time != 0)
				this.model.distribution();
			this.precTime = time;
			
			
			if(time == 0) {
				result = this.model.gameWinner();
			    System.out.println("risultato partita: "+result);
			    this.view.result(result);// passo il risultato alla view
			    this.quit();
			}
		}
		return this.model.situationPlayer();
	}
	
	@Override
	public String getName() {
		return this.playerName;
	}
	
	@Override
	public void setName(String name) {
		System.out.println(name);
		if(name != null)
			this.playerName = name;
		else
			this.playerName = "giocatore";
	}

	@Override
	public void bid(int n) {
		int bidAndTax;
		
		this.clickCount = 1;
	
		try {
			bidAndTax = n + 1;
			this.model.bid(n);
			if(n > 0)
				this.view.setText("\n"+this.playerName+" ha puntato: "+bidAndTax+" fiches");
			else
				this.view.setText("\n"+this.playerName+" ha passato il turno");
		} catch (BidIncorrectException e) {
			System.out.println("\n numero inserito non corretto");
			this.view.numberIncorrect();
			this.view.setText("\n"+this.playerName+" ha passato il turno");
		}
	}

	@Override
	public void fold() {
		this.clickCount = 1;
		try {
			this.model.bid(0); // paga tassa e passa
		} catch (BidIncorrectException e) {
			System.out.println("\n numero inserito non corretto");
			this.view.numberIncorrect();
		}
		
		System.out.println("passa il turno");
		this.view.setText("\n"+this.playerName+" ha passato il turno");	
	}

	@Override
	public void quit() {
		System.exit(0);	
	}
	
	public static void main(String[] args) {
		IndianPokerApp pockerIndianoApp = new IndianPokerApp();
		pockerIndianoApp.play();
	}

}
