import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUICancella extends JFrame {
	private static final long serialVersionUID = 5319156428702398764L;
	
	private JComboBox<String> lista;
	private JButton cancella;
	
	public GUICancella () {
		setTitle("Cancella una scadenza");
		setSize(600, 150);
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JLabel l = new JLabel("Scegliere la scadenza da cancellare"); //etichetta
		p.add(l);
		lista = new JComboBox<String>(EventiManager.getInstance().getDescrizioniEventi());
		p.add(lista);
		this.add(p, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel();
		cancella = new JButton("CANCELLA"); //bottone
		p2.add(cancella);
		this.add(p2, BorderLayout.SOUTH);

		cancella.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteCancella();
			}
		});
		
		this.getRootPane().setDefaultButton(cancella);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteCancella() {
		EventiManager eventiManager = EventiManager.getInstance();
		
		String daCancellare = lista.getSelectedItem().toString();
		if (eventiManager.rimuoviEvento(daCancellare)) {
			FileUtils.scriviScadenze();
			JOptionPane.showMessageDialog(null, "La scadenza è stata cancellata", "ATTENZIONE", JOptionPane.OK_CANCEL_OPTION);
			GUI.output.setText(Scadenze.creaAreaTesto(eventiManager).toString());
			// all'uscita dal popup chiudo la GUI
			this.dispose();
		}
	}
	
}
