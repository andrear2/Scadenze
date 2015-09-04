import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
	
	private static final String IT_FLAG = "images/it.png";
	private static final String EN_FLAG = "images/en.png";
	
	public static JTextArea output;
	private JLabel gestione;
	private JButton modifica, inserisci, cancella, mostra, gestioneMail;
	private MessaggiManager messaggiManager;
	
	public GUI (String testo) {
		// internazionalizzazione
		messaggiManager = MessaggiManager.getInstance();
		messaggiManager.setLocale(new Locale("it", "IT"));
		
		setSize(600, 200);
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel(new BorderLayout());
		gestione = new JLabel(); //etichetta
		p.add(gestione, BorderLayout.CENTER);
		JPanel flagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Image itaFlag = new ImageIcon(GUI.class.getClassLoader().getResource(IT_FLAG)).getImage();
		JButton itFlag = new JButton(new ImageIcon(itaFlag.getScaledInstance(25, 16, Image.SCALE_SMOOTH)));
		itFlag.setPreferredSize(new Dimension(30, 20));
		flagPanel.add(itFlag);
		Image engFlag = new ImageIcon(GUI.class.getClassLoader().getResource(EN_FLAG)).getImage();
		JButton enFlag = new JButton(new ImageIcon(engFlag.getScaledInstance(25, 16, Image.SCALE_SMOOTH)));
		enFlag.setPreferredSize(new Dimension(30, 20));
		flagPanel.add(enFlag);
		p.add(flagPanel, BorderLayout.EAST);
		this.add(p, BorderLayout.NORTH); //aggiungo il panel alla finestra in posizione nord
		
		output = new JTextArea(); //area di testo
		output.setEditable(false);
		JScrollPane sp = new JScrollPane(output); //barre di scorrimento 
		this.add(sp, BorderLayout.CENTER);
		output.append(testo); 

		JPanel p2 = new JPanel();
		modifica = new JButton();
		p2.add(modifica);
		inserisci = new JButton();
		p2.add(inserisci);
		cancella = new JButton();
		p2.add(cancella);
		mostra = new JButton();
		p2.add(mostra);
		gestioneMail = new JButton();
		p2.add(gestioneMail);
		this.add(p2, BorderLayout.SOUTH);
		
		setMessages();
		
		// Gestione eventi
		modifica.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					JFrame f = new GUIModifica();
					Scadenze.setIcon(f);
				} else {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("no_deadline_inserted"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		mostra.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					JFrame f = new GUIMostra();
					Scadenze.setIcon(f);
				} else {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("no_deadline_inserted"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		inserisci.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				JFrame f = new GUIInserisci();
				Scadenze.setIcon(f);
			}
		});
		
		cancella.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (EventiManager.getInstance().getEventi().size() > 0) {
					JFrame f = new GUICancella();
					Scadenze.setIcon(f);
				} else {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("no_deadline_inserted"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		gestioneMail.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				JFrame f = new GUIMailingList();
				Scadenze.setIcon(f);
			}
		});
		
		itFlag.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				MessaggiManager.getInstance().setLocale(new Locale("it", "IT"));
				setMessages();
				output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
			}
		});
		
		enFlag.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				MessaggiManager.getInstance().setLocale(new Locale("en", "US"));
				setMessages();
				output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void setMessages() {
		setTitle(messaggiManager.getMessaggi().getString("deadlines"));
		gestione.setText(messaggiManager.getMessaggi().getString("manage"));
		modifica.setText(messaggiManager.getMessaggi().getString("modify"));
		inserisci.setText(messaggiManager.getMessaggi().getString("insert"));
		cancella.setText(messaggiManager.getMessaggi().getString("delete"));
		mostra.setText(messaggiManager.getMessaggi().getString("show"));
		gestioneMail.setText(messaggiManager.getMessaggi().getString("mailing"));
	}
	
}
