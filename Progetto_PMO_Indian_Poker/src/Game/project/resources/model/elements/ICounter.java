package Game.project.resources.model.elements;

public interface ICounter {
	public int inc(int fiches, int fichesWon);
	public int dec(int fiches, int fichesToGiveAway);
}
