import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

public class GUIInserisci extends JFrame {
	private static final long serialVersionUID = 3267257491052047466L;
	
	private JButton inserisci;
	private JCalendar cal;
	private JTextField input;
	
	public GUIInserisci () {
		setTitle("Inserisci una scadenza");
		setSize(500, 350);	
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JLabel l = new JLabel("Inserire la data della nuova scadenza"); //etichetta
		p.add(l);
		cal = new JCalendar();
		p.add(cal);
		this.add(p, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("Inserire la descrizione della nuova scadenza");
		p2.add(l2);
		input = new JTextField(40);
		p2.add(input);
		this.add(p2, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel();
		inserisci = new JButton("INSERISCI"); 
		p3.add(inserisci);
		this.add(p3, BorderLayout.SOUTH);
		
		inserisci.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteInserisci();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteInserisci() {
		EventiManager eventiManager = EventiManager.getInstance();
		
		String descr = input.getText();
		if(descr.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Nessuna descrizione inserita", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (eventiManager.eventoGiaInserito(descr)) {
			JOptionPane.showMessageDialog(null, "La scadenza inserita esiste già", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
			return;
		}
		eventiManager.aggiungiEvento(cal.getCalendar(), descr);
		eventiManager.ordinaEventi();
		FileUtils.scriviScadenza(cal.getCalendar().getTime(), descr);
		JOptionPane.showMessageDialog(null, "Scadenza inserita", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
		GUI.output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
		// all'uscita dal popup chiudo la GUI
		this.dispose();
	}
}
