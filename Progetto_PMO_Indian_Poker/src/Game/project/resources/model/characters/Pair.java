package Game.project.resources.model.characters;

public class Pair<C, B> {
	private C card;
	private B bet;
	
	public Pair(C c, B b) {
		this.card = c;
		this.bet = b;
	}
	
	public C getCard() {
		return card;
	}
	
	public B getBet() {
		return bet;
	}
}
