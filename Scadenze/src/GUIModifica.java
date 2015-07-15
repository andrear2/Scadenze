import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

public class GUIModifica extends JFrame {
	private static final long serialVersionUID = -7855170248276999656L;
	
	private JComboBox<String> lista;
	private JCalendar cal;
	private JButton modifica;
	
	public GUIModifica () {
		EventiManager eventiManager = EventiManager.getInstance();
		List<Evento> eventi = eventiManager.getEventi();
		
		setTitle("Modifica una scadenza");
		setSize(500, 300);	
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JLabel l = new JLabel("Scegliere la scadenza da modificare"); //etichetta
		p.add(l);
		String[] descr = new String[eventi.size()];
		int i = 0;
		for (Evento e : eventi) {
			descr[i] = e.getDescr();
			i++;
		}
		lista = new JComboBox<String>(descr);
		lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Evento ev: EventiManager.getInstance().getEventi()) {
					if (lista.getSelectedItem().toString().equals(ev.getDescr())) {
						cal.setDate(ev.getCalendar().getTime());
					}
				}
			}
		});
		p.add(lista);
		this.add(p, BorderLayout.NORTH); //aggiungo il panel alla finestra in posizione nord
		
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("Scegliere la nuova data");
		p2.add(l2);
		cal = new JCalendar();
		p2.add(cal);
		this.add(p2, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel();
		modifica = new JButton("MODIFICA"); //bottone
		p3.add(modifica);
		this.add(p3, BorderLayout.SOUTH);
		
		modifica.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteModifica();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteModifica() {
		EventiManager eventiManager = EventiManager.getInstance();
		
		String descr = lista.getSelectedItem().toString();
		Calendar olddata = eventiManager.modificaEvento(cal.getCalendar(), descr);
		if (olddata != null) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			eventiManager.ordinaEventi();
			FileUtils.scriviScadenze();
			JOptionPane.showMessageDialog(null, "Scadenza modificata: " + descr + " da " + dateFormat.format(olddata.getTime()) + " a " + dateFormat.format(cal.getCalendar().getTime()), "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
			GUI.output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
			// all'uscita dal popup chiudo la GUI
			this.dispose();
		}
	}
}
