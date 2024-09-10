package Game.project.resources.view;

import Game.project.resources.model.enums.Result;

public  interface IndianPokerView {
	
	void setObserver(IndianPokerViewObserver observer);
	
	void start(); 

	void numberIncorrect();

	void setCount(String str);

	void setText(String str);
	
	void result(Result res);
}
