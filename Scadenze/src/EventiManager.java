import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class EventiManager {
	private static EventiManager instance = null;
	private List<Evento> eventi = new ArrayList<Evento>();
	
	private EventiManager() {
	}
	
	public static EventiManager getInstance() {
		if (instance == null) {
			instance = new EventiManager();
		}
		return instance;
	}

	public List<Evento> getEventi() {
		return eventi;
	}

	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}
	
	public String[] getDescrizioniEventi() {
		String[] descr = new String[eventi.size()];
		int i = 0;
		for (Evento e : eventi) {
			descr[i] = e.getDescr();
			i++;
		}
		return descr;
	}

	public void ordinaEventi() {
		Comparator<Evento> cmp = new Comparator<Evento>() {
			public int compare(Evento e1, Evento e2) {
				if ((e1.getDiff() - e2.getDiff()) < 0) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		Collections.sort(eventi, cmp);
	}
	
	public void aggiungiEvento(Calendar data, String descrizione) {
		eventi.add(new Evento(data, descrizione));
	}
	
	public boolean rimuoviEvento(String descrizione) {
		for (Evento e: eventi) {
			if (e.getDescr().compareTo(descrizione) == 0) {
				eventi.remove(e);
				return true;
			}
		}
		return false;
	}
	
	public Calendar modificaEvento(Calendar cal, String descrizione) {
		Calendar olddata = new GregorianCalendar();
		for (Evento e: eventi) {
			if (e.getDescr().compareTo(descrizione)==0) {
				olddata = e.getCalendar();
				e.setData(cal);
				return olddata;
			}
		}
		return null;
	}
	
	public boolean eventoGiaInserito (String descrizione) {
		for (Evento e: eventi) {
			if (e.getDescr().equals(descrizione)) {
				return true;
			}
		}
		return false;
	}
	
}
