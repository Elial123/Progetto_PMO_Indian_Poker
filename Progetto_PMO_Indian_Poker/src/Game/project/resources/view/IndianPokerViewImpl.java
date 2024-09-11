package Game.project.resources.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import Game.project.resources.model.enums.Result;


public class IndianPokerViewImpl implements IndianPokerView{

	private static final String FRAME_NAME = "Pocker Indiano App";
	private static final String QUIT = "Quit";
	private static final String BID = "Bid";
	private static final String FOLD = "Fold";
	private static final Dimension WINDOW_DIMENSION = new Dimension(420,420);
	
	private static final Map<Result,String> messages = Map.of(
			Result.YOU_lOST,"You lost the game",
			Result.YOU_WON,"You won the game!!",
			Result.YOU_DREW,"You drew");
	
	// observer
	private IndianPokerViewObserver observer;
	// Creo il frame, pannelli e bottoni
	private JFrame frame = new JFrame();// frame
	
    private final JPanel pEast;//pannello east
    private final JPanel pWest;// pannello west
    private final JPanel pWestInternal;// pannello interno west
    private final JLabel jLabel1; // label1 all'interno di pWestInternal
    private final JLabel jLabel2; // label2 all'interno di pWestInternal
    private final JScrollPane scroll;// JScrollPane che visualizza 
			                                                  // il contenuto del componente specificato
    private final JPanel pEastInternal; // Griglia flessibile
    private final GridBagConstraints cnst; // contenuto griglia flessibile
    private final JButton b1;
    private final JButton b2;
    private final JButton b3;
    private final JTextField bidPlayer;// inserimento bid
	
	// costruttore
	public IndianPokerViewImpl() {
		this.pEast = new JPanel(new FlowLayout());
		this.pWest = new JPanel(new FlowLayout());
		this.pWestInternal = new JPanel(new FlowLayout());
		this.jLabel1 = new JLabel();
		this.jLabel2 = new JLabel();
		this.scroll = new JScrollPane(pWestInternal);
		
		this.pEastInternal = new JPanel(new GridBagLayout());
		this.cnst = new GridBagConstraints();
		this.b1 = new JButton(BID);
		this.b2 = new JButton(FOLD);
		this.b3 = new JButton(QUIT);
		this.bidPlayer = new JTextField("1", 10);
		
		frame.setTitle(FRAME_NAME);
		frame.setSize(WINDOW_DIMENSION);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().add(pEast, BorderLayout.EAST);
		frame.getContentPane().add(pWest, BorderLayout.CENTER);
		
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// messaggio di controllo
		frame.addWindowListener((WindowListener) new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int number = JOptionPane.showConfirmDialog(frame,
						"Do you really want to quit?",
					    "Quitting..", JOptionPane.YES_NO_OPTION);
				if (number == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		// azioni pulsanti
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//boolean performed = true;
				try{
					//performed = observer.bid(Integer.parseInt(bidPlayer.getText()));
					observer.bid(Integer.parseInt(bidPlayer.getText()));
				} catch (NumberFormatException exception){
					JOptionPane.showMessageDialog(frame, "An integer please..");
				}
		}});
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (confirmDialog("Confirm quitting?","Quit")){
					observer.quit();
				}
		}});
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (confirmDialog("Confirm Folding?","Fold")){
					observer.fold();
				}
		}});
				
		// pannello pEast layout
		pEast.setBorder(new TitledBorder("Scelte"));
		// pannello pWest layout
		pWest.setBorder(new TitledBorder("Situazione partita"));
		// pannello pEastInternal layout
		pEastInternal.setBorder(new TitledBorder(""));
				
		cnst.gridy = 0;								  // 1-a riga
		cnst.insets = new Insets(48,3,48,3);			  // spazio attorno al comp.
		cnst.fill = GridBagConstraints.HORIZONTAL; 	  // estensione in orizzont.
		pEastInternal.add(bidPlayer,cnst);
		cnst.gridy++;
		pEastInternal.add(b1,cnst);
		cnst.gridy++;								  // prossima riga
		pEastInternal.add(b2,cnst);
		cnst.gridy++;
		pEastInternal.add(b3,cnst);
				
		// inserisci pannello nel pannello pEast
		pEast.add(pEastInternal);
			
		// inserisci in pWestInternal 2 label
		pWestInternal.add(jLabel1);
		pWestInternal.add(jLabel2);
				
		// inserisci pannello nel pannello pWest
		pWest.add(scroll);
				
				
		// imposta proprietà pannelli
		// East
		pEast.setOpaque(true);
		pEast.setBackground(Color.GREEN);
		// West
		pWest.setOpaque(true);
		pWest.setBackground(Color.GREEN);
		// pWestInternal
		pWestInternal.setPreferredSize(new Dimension(1300, 750));
		pWestInternal.setMinimumSize(new Dimension(1300, 750));
		pWestInternal.setOpaque(true);
		pWestInternal.setBackground(Color.WHITE);
		// pEastInternal
		pEastInternal.setOpaque(true);
		pEastInternal.setBackground(Color.WHITE);
	}
	
	@Override
	public void start(){
		// schermata di start
		JOptionPane.showMessageDialog(frame, "Starting Match");
		// schermata di input
		String name = JOptionPane.showInputDialog("Nome giocatore");
		observer.setName(name);
		this.frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "All'inizio di ogni turno dovrai pagare una tassa (1 fiches),"
				+"\nper poi decidere se puntare o passare."
				+ "\nNel caso non si prenda una decisione entro lo scadere del tempo:"
				+"\n verrà scalato l'ammontare della tassa dalle tue fiches."
				+"\nSi può puntare minimo 1 fiches e si perde quando si resta con 1 sola fiches.",
                "Rules",JOptionPane.PLAIN_MESSAGE);
	}
	
	private boolean confirmDialog(String question, String name){
		return JOptionPane.showConfirmDialog(frame,question,name,JOptionPane.YES_NO_OPTION) 
				== JOptionPane.YES_OPTION;
	}
	@Override
	public void setObserver(IndianPokerViewObserver observer){
		this.observer = observer;
	}
	@Override
	public void numberIncorrect() {
		JOptionPane.showMessageDialog(frame, "il numero inserito non è corretto", 
				"Incorrect Number", JOptionPane.ERROR_MESSAGE);
	}
	@Override
	public void setCount(String str) {
		this.jLabel1.setText(str);
	}
	@Override
	public void setText(String str) {
		this.jLabel2.setText(str);
	}
	@Override
	public void result(Result res) {
	    JOptionPane.showMessageDialog(frame, messages.get(res),
	                                  "Result",JOptionPane.PLAIN_MESSAGE);
	}

}
