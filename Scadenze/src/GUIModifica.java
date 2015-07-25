import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;

import com.toedter.calendar.JCalendar;

public class GUIModifica extends JFrame {
	private static final long serialVersionUID = -7855170248276999656L;
	
	private JComboBox<String> lista;
	private JCalendar cal;
	private JCheckBox allDay;
	private JSpinner time;
	private JTextField input;
	private JButton modifica;
	private boolean varAllDay;
	
	public GUIModifica () {
		EventiManager eventiManager = EventiManager.getInstance();
		List<Evento> eventi = eventiManager.getEventi();
		
		setTitle("Modifica una scadenza");
		setSize(700, 350);
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
						setFields(ev);
					}
				}
			}
		});
		p.add(lista);
		this.add(p, BorderLayout.NORTH); //aggiungo il panel alla finestra in posizione nord
		
		JPanel innerPanel = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("Scegliere la nuova data");
		p2.add(l2);
		cal = new JCalendar();
		p2.add(cal);
		allDay = new JCheckBox("Tutto il giorno");
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
		p2.add(allDay);
		time = new JSpinner(new SpinnerDateModel());
		DateEditor timeEditor = new DateEditor(time, "HH:mm");
		time.setEditor(timeEditor);
		p2.add(time);
		innerPanel.add(p2, BorderLayout.NORTH);
		
		JPanel p3 = new JPanel();
		JLabel l3 = new JLabel("Descrizione");
		p3.add(l3);
		input = new JTextField(40);
		p3.add(input);
		innerPanel.add(p3, BorderLayout.CENTER);
		this.add(innerPanel, BorderLayout.CENTER);
		
		JPanel p4 = new JPanel();
		modifica = new JButton("MODIFICA"); //bottone
		p4.add(modifica);
		this.add(p4, BorderLayout.SOUTH);
		
		setFields(eventi.get(0));
		
		modifica.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteModifica();
			}
		});
		
		this.getRootPane().setDefaultButton(modifica);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteModifica() {
		EventiManager eventiManager = EventiManager.getInstance();
		
		String descr = lista.getSelectedItem().toString();
		Calendar data = cal.getCalendar();
		if (!varAllDay) {
			Date ora = (Date) time.getValue();
			data.set(Calendar.HOUR_OF_DAY, ora.getHours());
			data.set(Calendar.MINUTE, ora.getMinutes());
		}
		Evento eOld = eventiManager.modificaEvento(data, varAllDay, input.getText(), descr);
		if (eOld != null) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			eventiManager.ordinaEventi();
			FileUtils.scriviScadenze();
			String da, a;
			if (eOld.isAllDay()) {
				da  = dateFormat.format(eOld.getData().getTime());
			} else {
				da  = dateTimeFormat.format(eOld.getData().getTime());
			}
			if (varAllDay) {
				a  = dateFormat.format(cal.getCalendar().getTime());
			} else {
				a  = dateTimeFormat.format(cal.getCalendar().getTime());
			}
			JOptionPane.showMessageDialog(null, "Scadenza modificata: " + descr + " da " + da + " a " + a, "ATTENZIONE", JOptionPane.INFORMATION_MESSAGE);
			GUI.output.setText(Scadenze.creaAreaTesto(EventiManager.getInstance()).toString());
			// all'uscita dal popup chiudo la GUI
			this.dispose();
		}
	}
	
	private void setFields(Evento evento) {
		cal.setDate(evento.getData().getTime());
		varAllDay = evento.isAllDay();
		allDay.setSelected(varAllDay);
		Date date = new Date();
		if (varAllDay) {
			date.setHours(0);
			date.setMinutes(0);
		} else {
			date.setHours(evento.getData().getTime().getHours());
			date.setMinutes(evento.getData().getTime().getMinutes());
		}
		time.setValue(date);
		time.setVisible(!varAllDay);
		input.setText(evento.getDescr());
	}
}
