import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.*;

import com.toedter.calendar.JCalendar;


public class GUIMostra extends JFrame {
	private static final long serialVersionUID = -8040491774448916851L;
	
	private JButton mostra, tutte;
	private JTextArea output;
	private JCalendar cal;
	
	public GUIMostra () {
		setTitle("Mostra scadenze");
		setSize(700, 450);
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		mostra = new JButton("MOSTRA FINO A"); 
		p.add(mostra);
		cal = new JCalendar();
		p.add(cal);
		tutte = new JButton("MOSTRA TUTTE");
		p.add(tutte);
		this.add(p, BorderLayout.NORTH); 
		
		output = new JTextArea(12,25); //area di testo
		output.setEditable(false);
		JScrollPane sp = new JScrollPane(output); //in questo modo ho le barre di scorrimento
		sp.setVisible(true);
		this.add(sp, BorderLayout.SOUTH);
		
		mostra.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteMostra();
			}
		});
		tutte.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				clickSulPulsanteTutte();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void clickSulPulsanteMostra() {
		output.setText("");
		StringBuffer out = new StringBuffer();
		Calendar c = cal.getCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		for (Evento e : EventiManager.getInstance().getEventi()) {
			long diffMilliSec = e.getData().getTimeInMillis() - c.getTimeInMillis();
			long diffGiorni = diffMilliSec / (24 * 60 * 60 * 1000);
			if (diffGiorni <= 0) {
				out.append(e.toString3());
			}
		}
		if (out.length() == 0) {
			out.append("Non ci sono scadenze precedenti alla data indicata");
		}
		output.append(out.toString()); 
	}
	
	public void clickSulPulsanteTutte() {
		output.setText("");
		StringBuffer out = new StringBuffer();
		for (Evento e : EventiManager.getInstance().getEventi()) {
			out.append(e.toString3());
		}
		output.append(out.toString()); 
	}
	
}