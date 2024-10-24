package Game.project.resources.controller;

import Game.project.resources.model.match.BidIncorrectException;
import Game.project.resources.model.match.Match;
import Game.project.resources.model.match.Match2;
import Game.project.resources.view.IndianPokerViewObserver;
import Game.project.resources.view.PokerGameView;
import Game.project.resources.view.IndianPokerViewImpl;

import Game.project.resources.model.enums.Result;

import java.util.List;

import Game.project.resources.model.characters.BotPlayer;
import Game.project.resources.model.characters.Dealer;
import Game.project.resources.model.characters.HumanPlayer;
import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.GameStopwatch;

public class PokerGame implements IndianPokerViewObserver{
	
	private final Match model;
	private final PokerGameView view;
	
	private final GameStopwatch gameStopwatch; 
	private String playerName;
	private boolean mode;
    private int clickCount;
    private Result result;
    private int precTime;
	
    public PokerGame() {
		this.clickCount = 0;
		this.mode = false;
		this.model = new Match(Dealer.getInstance(), new HumanPlayer(), new BotPlayer(), new BotPlayer(), new BotPlayer()); 
		this.view = new PokerGameView();
		this.view.setObserver(this);
		this.view.start();
		this.gameStopwatch = model.getTimer();
		this.precTime = 0;
	}
	
	@Override
	public void play() {
		int index;
		List<Player> list;
	    gameStopwatch.start();
	    list = this.model.start(playerName, mode);
	    this.model.distribution();
	   
        
        do {
        	if(precTime != gameStopwatch.getTime()/1000) {
        		precTime++; 
        		this.view.setTimer(precTime/60, precTime%60);
        		if(precTime == 1) {
        			
        			this.model.playRound();
        			this.view.updateTurn(0);
        			this.updatePlayersView(list, false);
        		}
        			
        			//this.view.setText(this.model.situationPlayer());
        		/*if(precTime == 16 || precTime == 31 || precTime == 46 || precTime == 61 || precTime == 76
        			|| precTime == 91 || precTime == 106 || precTime == 121 || precTime == 136 || precTime == 151)*/
        		if((precTime - 16) % 15 == 0 && precTime >= 16 && precTime <= 151) {
        			
        			System.out.println("\nshowdown-------------------------------------\n");
        			
        			gameStopwatch.stop();
        			list = this.game(precTime);
        			
        			this.updatePlayersView(list, true);
        			
        			this.outOfOrder(list);
        			
        			 try {
                         Thread.sleep(3000);
                     } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                     }
        			
        			gameStopwatch.start();
        		}
        		
        		if(precTime == 17 || precTime == 32 || precTime == 47 || precTime == 62 || precTime == 77
        		   || precTime == 92 || precTime == 107 || precTime == 122 || precTime == 137) {
        			
        			System.out.println("\ngame------------------------------------");
        			
        			index = this.model.getTurn();
        		    this.model.distribution();
        			this.model.playRound();
        			list = this.model.situationPlayer();
        			
        			this.view.updateTurn(index);
        			this.updatePlayersView(list, false);
        			
        		}
        	}
        	
        }while(precTime < 151);
        gameStopwatch.stop();
        gameStopwatch.reset();
        
		
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
	public void bid(int bid) {
		int bidAndTax;
		
		this.clickCount = 1;
	
		try {
			bidAndTax = bid + 1;
			this.model.bid(bid);
			if(bid > 0)
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
		PokerGame pockerIndianoApp = new PokerGame();
		pockerIndianoApp.play();
	}

}
