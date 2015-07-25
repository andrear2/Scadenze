import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Scadenze {
	public static final String SCADENZE = "Scadenze.txt";
	public static final String EMAIL = "Email.txt";
	public static final String ICON = "images/schedule.png";
	
	private static boolean inviaMail = true;
	
	public static void main(String[] args) {
		EventiManager eventiManager = EventiManager.getInstance();
		
		FileUtils.creaFile(EMAIL);

		FileUtils.creaFile(SCADENZE);
		FileUtils.leggiScadenze();
		
		eventiManager.ordinaEventi();
		
		StringBuffer output = creaAreaTestoEInviaMail(eventiManager);
		inviaMail = false;
		JFrame f = new GUI(output.toString());
		f.setIconImage(new ImageIcon(ICON).getImage());
	}

	public static StringBuffer creaAreaTestoEInviaMail(EventiManager eventiManager) {
		StringBuffer output = new StringBuffer();
		for (Evento e : eventiManager.getEventi()) {
			if (e.getDiff() < 0) {
				output.append(e.toString1());
				if (e.getDiff() == -1 && inviaMail) {
					MailUtils.inviaMail("Ieri è scaduto " + e.getDescr());
				}
			} else if (e.getDiff() == 0) {
				output.append(e.toString2());
				if (inviaMail) {
					MailUtils.inviaMail("ATTENZIONE: oggi scade " + e.getDescr());
				}
			} else if (e.getDiff() > 0 && e.getDiff() <= 15) {
				output.append(e.toString3());
				if (inviaMail) {
					if (e.getDiff() == 15) {
						MailUtils.inviaMail("Tra 15 giorni scade " + e.getDescr());
					} else if (e.getDiff() == 1) {
						MailUtils.inviaMail("Domani scade " + e.getDescr());
					} else if (e.getDiff() == 5) {
						MailUtils.inviaMail("Tra 5 giorni scade " + e.getDescr());
					}
				}
			}
		}
		if (output.length() == 0) {
			output.append("Non ci sono scadenze in programma nei prossimi 15 giorni. Buona giornata :)");
		}
		return output;
	}
	
	public static StringBuffer creaAreaTesto(EventiManager eventiManager) {
		StringBuffer output = new StringBuffer();
		for (Evento e : eventiManager.getEventi()) {
			if (e.getDiff() < 0) {
				output.append(e.toString1());
			} else if (e.getDiff() == 0) {
				output.append(e.toString2());
			} else if (e.getDiff() > 0 && e.getDiff() <= 15) {
				output.append(e.toString3());
			}
		}
		if (output.length() == 0) {
			output.append("Non ci sono scadenze in programma nei prossimi 15 giorni. Buona giornata :)");
		}
		return output;
	}
	
}
