package Game.project.resources.view;

import javax.swing.*;
import java.awt.*;

public class MatchResult extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MatchResult(String risultato) {
        // Imposta il titolo della finestra
        super("Risultato Partita");
        
        // Configura le impostazioni della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Centro della schermata

        // Cambia il colore dello sfondo in base al risultato
        switch (risultato.toLowerCase()) {
            case "you won the game!!":
                getContentPane().setBackground(Color.GREEN);
                break;
            case "you drew":
                getContentPane().setBackground(Color.BLUE);
                break;
            case "you lost the game":
                getContentPane().setBackground(Color.RED);
                break;
            default:
                getContentPane().setBackground(Color.WHITE);
                break;
        }

        // Aggiungi un'etichetta con il messaggio e configura l'allineamento
        JLabel label = new JLabel(risultato, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.BLACK); // Colore del testo
        add(label, BorderLayout.CENTER);

        // Rendi la finestra visibile
        setVisible(true);
    }
}

