package Game.project.resources.view;

import javax.swing.*;
import javax.swing.border.LineBorder;

import Game.project.resources.model.enums.Result;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PokerGameView extends JFrame implements IPokerGameView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel timeLabel;
    private PlayerPanel[] playerPanels;
    private JButton betButton;
    private JButton foldButton;
    private JButton quitButton;

    private Image backgroundImage;
    
    private IndianPokerViewObserver observer;
    
    private static final Map<Result,String> messages = Map.of(
			Result.YOU_lOST,"You lost the game",
			Result.YOU_WON,"You won the game!!",
			Result.YOU_DREW,"You drew");

	public PokerGameView() {
        setTitle("Poker Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Imposta cursore
        Cursor cursore = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        setCursor(cursore);
        
        // Imposta lo sfondo del JFrame a nero
        getContentPane().setBackground(Color.BLACK);

        // Carica l'immagine di sfondo dei PlayerPanel
        backgroundImage = new ImageIcon("img/poker.jpg").getImage();

        timeLabel = new JLabel("Tempo: 00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setForeground(Color.RED);
        add(timeLabel, BorderLayout.NORTH);

        // Creazione del pannello per i player giocanti
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(2, 2));
        playerPanels = new PlayerPanel[4];
        for (int i = 0; i < 4; i++) {
            playerPanels[i] = new PlayerPanel("Giocatore " + (i + 1), 10, "img/Carta-Coperta.jpg", "img/Carta-Coperta.jpg");
            playersPanel.add(playerPanels[i]);
        }
        add(playersPanel, BorderLayout.CENTER);

        // Creazione del pannello per i pulsanti
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));  // 1 riga, 3 colonne
        buttonsPanel.setBackground(Color.BLACK);
        
        // Aggiunta margine per distanziare il pannello dal blocco precedente
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 0));  // Distanza 8px sopra e 5px sotto

        // Creazione e configurazione dei pulsanti
        betButton = new JButton(new ImageIcon("img/button.png"));
        configureButton(betButton, Color.GREEN);
        JLabel betLabel = new JLabel("Puntare", SwingConstants.CENTER);
        betLabel.setForeground(Color.WHITE);
        betButton.setLayout(new BorderLayout());
        betButton.add(betLabel, BorderLayout.NORTH);

        foldButton = new JButton(new ImageIcon("img/button.png"));
        configureButton(foldButton, Color.CYAN);
        JLabel foldLabel = new JLabel("Foldare", SwingConstants.CENTER);
        foldLabel.setForeground(Color.WHITE);
        foldButton.setLayout(new BorderLayout());
        foldButton.add(foldLabel, BorderLayout.NORTH);

        quitButton = new JButton(new ImageIcon("img/button.png"));
        configureButton(quitButton, Color.RED);
        JLabel quitLabel = new JLabel("Quittare", SwingConstants.CENTER);
        quitLabel.setForeground(Color.WHITE);
        quitButton.setLayout(new BorderLayout());
        quitButton.add(quitLabel, BorderLayout.NORTH);

        // Aggiunta dei pulsanti al pannello
        buttonsPanel.add(betButton);
        buttonsPanel.add(foldButton);
        buttonsPanel.add(quitButton);
        
        // Aggiunta del pannello dei pulsanti al frame
        add(buttonsPanel, BorderLayout.SOUTH);

        // Event listener per il pulsante "Puntare"
        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String betAmount = JOptionPane.showInputDialog("Inserisci l'importo della puntata:");
                // Prova a convertire l'input in un intero
                try {
                    int numeroIntero = Integer.parseInt(betAmount);
                    observer.bid(numeroIntero);
                } catch (NumberFormatException exception) { // se ha fallito -> Errore
                    JOptionPane.showMessageDialog(null, "Errore: Inserisci un numero intero valido!");
                }
                // Implementare la logica per gestire la puntata
                System.out.println("Puntata: " + betAmount);
            }
        });

        // Event listener per il pulsante "Foldare"
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	observer.fold();
                System.out.println("Il giocatore ha foldato.");
            }
        });

        // Event listener per il pulsante "Quitare"
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (confirmDialog("Confermi di voler lasciare la partita?","Quit")){
            		System.exit(0);
            	}
            }
        });
    }
	
	// Metodo per configurare i pulsanti
	private void configureButton(JButton button, Color backgroundColor) {
	    button.setFocusPainted(false);
	    button.setContentAreaFilled(false);
	    button.setBorderPainted(false);
	    button.setBackground(backgroundColor);
	}
    
	// Metodo di start della view
	@Override
	public void start(){
		// schermata di start
		JOptionPane.showMessageDialog(this, "Starting Match");
		// schermata di input
		String name = JOptionPane.showInputDialog("Inserire nome giocatore");
		observer.setName(name);
		
		int response = JOptionPane.showConfirmDialog(this, 
				"Vuoi giocare a coppie?",
				"Scelta",
				JOptionPane.YES_NO_OPTION);
		boolean pair;
		if(response == JOptionPane.YES_OPTION)
			pair = true;
		else
			pair = false;
		observer.setMod(pair);
		
		JOptionPane.showMessageDialog(this, "Ci sono 10 round, ognuno di essi dura: 15s.\n"
				+ "All'inizio di ogni turno dovrai pagare una tassa (1 fiches),"
				+"\nper poi decidere se puntare o passare."
				+"\nInizia il turno il giocatore con il bordo verde."
				+ "\nNel caso non si prenda una decisione entro lo scadere del tempo:"
				+"\n verrà scalato l'ammontare della tassa dalle tue fiches."
				+"\nSi può puntare minimo 1 fiches e massimo 4.\n"
				+"Si perde quando si resta con 1 sola fiches.",
                "Rules",JOptionPane.PLAIN_MESSAGE);
		this.setVisible(true);
	}
	
	// Metodo che conferma l'inserimento della scelta di modalità da parte dell'utente
	private boolean confirmDialog(String question, String name){
		return JOptionPane.showConfirmDialog(this,question,name,JOptionPane.YES_NO_OPTION) 
				== JOptionPane.YES_OPTION;
	}
	
	// Metodo che setta l'observer
	@Override
	public void setObserver(IndianPokerViewObserver observer){
		this.observer = observer;
	}
	
	// Metodo che segnala errore durante l'inserimento al giocatore
	@Override
	public void numberIncorrect(String ms) {
		JOptionPane.showMessageDialog(this, ms, 
				"Incorrect Number", JOptionPane.ERROR_MESSAGE);
	}
	
	// Metodo che stampa il risultato della partita
	@Override
	public void result(Result res) {
	   
		String message = messages.get(res);
	    // Prima apriamo la nuova finestra
	    new MatchResult(message);
	    // Dopo chiudiamo la finestra corrente
	    //this.dispose(); 
	}

    // Metodo per aggiornare il timer
	@Override
    public void setTimer(int minutes, int seconds) {
        timeLabel.setText(String.format("Tempo: %02d:%02d", minutes, seconds));
    }

    // Metodo per aggiornare le informazioni di un giocatore
	@Override
    public void updatePlayer(int playerIndex, String name, int chips, int bid, boolean fold, String cardImagePath1, String cardImagePath2) {
        if (this.indexControle(playerIndex)) {
            playerPanels[playerIndex].setPlayerDetails(name, chips, bid, fold, cardImagePath1, cardImagePath2);
        }
    }
    
	// Metodo per aggiornare i turni
	@Override
    public void updateTurn(int index) {
    	for(int i = 0; i < playerPanels.length; i++)
    		if (i == index) 
    			playerPanels[i].setBorder(new LineBorder(Color.GREEN, 10)); // 5 è la larghezza del bordo;
    		else
    			playerPanels[i].setBorder(new LineBorder(Color.RED, 5));
    }
    
	// Metodo che esegue il controllo degli indici
    private boolean indexControle(int index) {
    	if (index >= 0 && index < playerPanels.length)
    		return true;
    	else 
    		return false;
    }

    // Classe interna per gestire il pannello di ogni giocatore
    private class PlayerPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel nameLabel;
        private JLabel chipsLabel;
        private JLabel cardLabel1;
        private JLabel cardLabel2;

        public PlayerPanel(String playerName, int chips, String cardImagePath1, String cardImagePath2) {
            setLayout(new BorderLayout());
            nameLabel = new JLabel(playerName);
            nameLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24)); // misto di due stili
            nameLabel.setForeground(new Color(144, 238, 144)); // verde chiaro
            
            chipsLabel = new JLabel("Chips: " + chips);
            chipsLabel.setFont(new Font("Arial", Font.BOLD, 20));
            chipsLabel.setForeground(new Color(255, 182, 103)); // arancione chiaro
      
            cardLabel1 = new JLabel(new ImageIcon(cardImagePath1));
            cardLabel2 = new JLabel(new ImageIcon(cardImagePath2));

            add(nameLabel, BorderLayout.NORTH);
            add(chipsLabel, BorderLayout.CENTER);
            JPanel cardsPanel = new JPanel();
            cardsPanel.add(cardLabel1);
            cardsPanel.add(cardLabel2);
            add(cardsPanel, BorderLayout.SOUTH);

            LineBorder border = new LineBorder(Color.RED, 5);
            setBorder(border);
            cardsPanel.setBackground(new Color(0, 100, 0, 150)); // colore verde scuro con trasparenza
            setBackground(Color.BLACK);
        }

        // Metodo per aggiornare i dettagli del giocatore
        public void setPlayerDetails(String playerName, int chips, int bid, boolean fold, String cardImagePath1, String cardImagePath2) {
            nameLabel.setText(playerName);
            chipsLabel.setText("Chips: " + chips+"  Bet: " + bid+" Fold: "+fold);
            cardLabel1.setIcon(new ImageIcon(cardImagePath1));
            cardLabel2.setIcon(new ImageIcon(cardImagePath2));
        }
        
        // Metodo per disegnare i componenti del PlayerPanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
