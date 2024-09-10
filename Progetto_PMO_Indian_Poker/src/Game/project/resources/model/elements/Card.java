package Game.project.resources.model.elements;

public class Card implements ICard{
	private int value;
	private int seed;
	private static final String[] v ={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	private static final String[] s ={"quadri","cuori","fiori","picche"};
	
	public Card(int v, int s) {//Costruttore
		this.value = v; 
		this.seed = s; 
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
		String str = v[this.value] + " di " + s[this.seed];
		return str; 
	}

}
