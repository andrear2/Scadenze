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
		for (Evento e: eventi) {
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
	
	public void aggiungiEvento(Calendar data, boolean allDay, String descrizione) {
		eventi.add(new Evento(data, allDay, descrizione));
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
	
	public Evento modificaEvento(Calendar cal, boolean allDay, String newDescr, String descrizione) {
		for (Evento e: eventi) {
			if (e.getDescr().compareTo(descrizione) == 0) {
				Evento eOld = new Evento(e.getData(), e.isAllDay(), e.getDescr());
				if (allDay) {
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
				}
				e.setDescr(newDescr);
				e.setData(cal);
				e.setAllDay(allDay);
				e.setDiff(e.calcolaDiff());
				return eOld;
			}
		}
		return null;
	}
	
	public boolean isEventoGiaInserito(String descrizione) {
		for (Evento e: eventi) {
			if (e.getDescr().equals(descrizione)) {
				return true;
			}
		}
		return false;
	}
	
}
