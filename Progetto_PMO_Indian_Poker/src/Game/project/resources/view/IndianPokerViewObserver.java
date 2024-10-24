package Game.project.resources.view;

public interface IndianPokerViewObserver {
	
	void play();
	
    String getName();
	
	void setName(String name);
	
	void bid(int n);
	
	void fold();
	
	void quit();

	void setMod(boolean pair);

	boolean getMod();
}
