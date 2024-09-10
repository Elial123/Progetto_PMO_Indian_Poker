package Game.project.resources.model.match;

import Game.project.resources.model.elements.GameTimer;
import Game.project.resources.model.enums.Result;

public interface IMatch {
	
	public void start(String name);
	
	public GameTimer getTimer();
	
	public void distribution();
	
	public String situationPlayer();
	
	public void payTaxes();
	
	public void fold(int index);
	
	public void bid(int b) throws BidIncorrectException;
	
	public Result roundOver();
	
	public void roundWinner();
	
	public Result gameWinner();
}
