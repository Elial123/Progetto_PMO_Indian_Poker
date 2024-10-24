package Game.project.resources.view;

import Game.project.resources.model.enums.Result;

public interface IPokerGameView {
	
    void setObserver(IndianPokerViewObserver observer);
	
	void start(); 

	void numberIncorrect(String ms);

	void setTimer(int minutes, 
				  int seconds);
	
	void updatePlayer(int playerIndex, 
					  String name, 
					  int chips, 
					  int bid, 
					  boolean fold, 
					  String cardImagePath1, 
					  String cardImagePath2);
	
	void updateTurn(int index);
	
	void result(Result res);
}
