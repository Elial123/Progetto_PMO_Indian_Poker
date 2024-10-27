package Game.project.resources.model.match;

import java.util.ArrayList;

import Game.project.resources.model.characters.Player;
import Game.project.resources.model.elements.GameStopwatch;
import Game.project.resources.model.enums.Result;

public interface IMatch {
	
	ArrayList<Player> start(String name, boolean choice);

	GameStopwatch getTimer();
	
	int getTurn();
	
	void distribution();
	
	ArrayList<Player> situationPlayer();
	
	void playRound();
	
	void bid(int b) throws BidIncorrectException;
	
	Result roundOver();
	
	void roundWinner();
	
	Result gameWinner();
}
