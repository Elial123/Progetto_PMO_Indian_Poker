package Game.project.resources.model.elements;

import java.util.ArrayList;


public class Deck implements IDeck{
	private Card[] card;
	private int i;
    private int j;
    private ArrayList<Card> extractionList;
    
    // Costruttore 
 	public Deck() {	
 		this.i = 0;
 		this.j = 0;
 		this.card = new Card[40];
 		this.extractionList = new ArrayList<Card>();
        extractionList.ensureCapacity(40);
        this.deckInitializetion();
 	    
 	}
 	
 	private void deckInitializetion() {
 		for (int s=0;s<= 3;s++)  	
 	        for (int v=0; v<10;v++){
 	             card[i] = new Card(v, s);
 	             i++;
 	    }
     }
	
 	@Override
	public Card getCard() {
		do{			
		    j = (int)(Math.random()*40);
		}
		while(extractionList.contains(card[j]));//se è già contenuta nella lista riestrae
	    extractionList.add(card[j]); // aggiunge nuova carta
	    //System.out.println("j: "+j);
		return card[j];
	}
	@Override
	public void reEnteringCards() {
		this.extractionList.clear();
	}
}
