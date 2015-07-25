import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {
	private static final long serialVersionUID = -6473494817210166309L;
	
	private static final String NESSUNA_SCADENZA_INSERITA = "ATTENZIONE: non hai ancora inserito nessuna scadenza";
	private static final String ATTENZIONE = "ATTENZIONE";
	private static final String MODIFICA = "MODIFICA";
	private static final String INSERISCI = "INSERISCI";
	private static final String CANCELLA = "CANCELLA";
	private static final String MOSTRA = "MOSTRA";
	private static final String MAILING_LIST = "MAILING LIST";
	
	public static JTextArea output;
	private JButton modifica, mostra, inserisci, cancella, gestioneMail;
	
	public GUI (String testo) {
		setTitle("Scadenze");
		setSize(600, 200);	
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JLabel l = new JLabel("Gestione scadenze"); //etichetta
		p.add(l);
		this.add(p, BorderLayout.NORTH); //aggiungo il panel alla finestra in posizione nord
		
		output = new JTextArea(); //area di testo
		output.setEditable(false);
		JScrollPane sp = new JScrollPane(output); //barre di scorrimento 
		this.add(sp, BorderLayout.CENTER);
		output.append(testo); 

		JPanel p2 = new JPanel();
		modifica = new JButton(MODIFICA); //bottone
		p2.add(modifica);
		inserisci = new JButton(INSERISCI); //bottone
		p2.add(inserisci);
		cancella = new JButton(CANCELLA); //bottone
		p2.add(cancella);
		mostra = new JButton(MOSTRA);
		p2.add(mostra);
		gestioneMail = new JButton(MAILING_LIST);
		p2.add(gestioneMail);
		this.add(p2, BorderLayout.SOUTH);
		
		//Gestione eventi
		modifica.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					clickSulPulsanteModifica();
				} else {
					JOptionPane.showMessageDialog(null, NESSUNA_SCADENZA_INSERITA, ATTENZIONE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mostra.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					clickSulPulsanteMostra();
				} else {
					JOptionPane.showMessageDialog(null, NESSUNA_SCADENZA_INSERITA, ATTENZIONE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		inserisci.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteInserisci();
			}
		});
		cancella.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					clickSulPulsanteCancella();
				} else {
					JOptionPane.showMessageDialog(null, NESSUNA_SCADENZA_INSERITA, ATTENZIONE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		gestioneMail.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteGestioneMail();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteMostra() {
		JFrame f = new GUIMostra();
		f.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Scadenze.ICON)).getImage());
	}
	
	public void clickSulPulsanteModifica() {
		JFrame f = new GUIModifica();
		f.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Scadenze.ICON)).getImage());
	}
	
	public void clickSulPulsanteInserisci() {
		JFrame f = new GUIInserisci();
		f.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Scadenze.ICON)).getImage());
	}

	public void clickSulPulsanteCancella() {
		JFrame f = new GUICancella();
		f.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Scadenze.ICON)).getImage());
	}
	
	public void clickSulPulsanteGestioneMail() {
		JFrame f = new GUIMailingList();
		f.setIconImage(new ImageIcon(Scadenze.ICON).getImage());
	}
}
