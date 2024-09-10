package Game.project.resources.model.characters;

import java.util.ArrayList;

import Game.project.resources.model.elements.Card;

public interface IDealer {
	public String getName();
	public void setName(String name);
	public Card distribute();
	public int winner(ArrayList<Player> characters);
	public void calculateNumberOfFiches(ArrayList<Player> characters, int winnerNumber, int fichesWon);
	public void shuffle();
	public String toString();
}
