package Game.project.resources.model.elements;

public class Card implements ICard{
	private int value;
	private int seed;
	private static final String[] values ={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	private static final String[] seeds ={"quadri","cuori","fiori","picche"};
	
	public Card(int value, int seed) {//Costruttore
		this.value = value; 
		this.seed = seed; 
	}
	
	@Override
	public int getValue(){
		return value;
	}
	@Override
	public int getSeed(){
		return seed;
	}
	@Override
	public String toString() {
		String str = values[this.value] + " di " + seeds[this.seed];
		return str; 
	}

}
