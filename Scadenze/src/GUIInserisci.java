import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JCalendar;

public class GUIInserisci extends JFrame {
	private static final long serialVersionUID = 3267257491052047466L;
	
	private JButton inserisci;
	private JCalendar cal;
	private JCheckBox allDay;
	private JSpinner time;
	private JTextField input;
	private boolean varAllDay;
	
	public GUIInserisci () {
		setTitle("Inserisci una scadenza");
		setSize(700, 350);
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JLabel l = new JLabel("Inserire la data della nuova scadenza"); //etichetta
		p.add(l);
		cal = new JCalendar();
		p.add(cal);
		allDay = new JCheckBox("Tutto il giorno");
		allDay.setSelected(true);
		varAllDay = true;
		allDay.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItemSelectable().equals(allDay)) {
					time.setVisible(false);
					varAllDay = true;
				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					time.setVisible(true);
					varAllDay = false;
				}
			}
		});
		p.add(allDay);
		time = new JSpinner(new SpinnerDateModel());
		DateEditor timeEditor = new DateEditor(time, "HH:mm");
		time.setEditor(timeEditor);
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		time.setValue(date);
		time.setVisible(false);
		p.add(time);
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
		
		this.getRootPane().setDefaultButton(inserisci);
		
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
		if (eventiManager.isEventoGiaInserito(descr)) {
			JOptionPane.showMessageDialog(null, "La scadenza inserita esiste già", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Calendar data = cal.getCalendar();
		if (!varAllDay) {
			Date ora = (Date) time.getValue();
			data.set(Calendar.HOUR_OF_DAY, ora.getHours());
			data.set(Calendar.MINUTE, ora.getMinutes());
		}
		if (data.before(new GregorianCalendar())) {
			int scelta = JOptionPane.showConfirmDialog(null, "La data inserita è precedente alla data odierna. Inserire comunque la scadenza?", "ATTENZIONE", JOptionPane.OK_CANCEL_OPTION);
			if (scelta != 0) {
				return;
			}
		}
		eventiManager.aggiungiEvento(data, varAllDay, descr);
		eventiManager.ordinaEventi();
		FileUtils.scriviScadenza(data.getTime(), varAllDay, descr);
		JOptionPane.showMessageDialog(null, "Scadenza inserita", "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
		GUI.output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
		// all'uscita dal popup chiudo la GUI
		this.dispose();
	}
}
