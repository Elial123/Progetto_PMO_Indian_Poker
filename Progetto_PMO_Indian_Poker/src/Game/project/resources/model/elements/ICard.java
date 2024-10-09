package Game.project.resources.model.elements;

import Game.project.resources.model.enums.Suits;

public interface ICard {
	
	public Integer getRank();
	
	public Suits getSuit();
	
	public String toString();
}
