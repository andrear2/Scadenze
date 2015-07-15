import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Evento {
	
	private DateFormat dateFormat;
	private Calendar data;
	private String descr;
	private long diff;
	
	public Evento (Calendar data, String descr) {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.data = data;
		this.data.set(Calendar.HOUR_OF_DAY, 0);
		this.data.set(Calendar.MINUTE, 0);
		this.data.set(Calendar.SECOND, 0);
		this.data.set(Calendar.MILLISECOND, 0);
		this.descr = descr;
		Calendar now = new GregorianCalendar();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		long diffMilliSec = this.data.getTimeInMillis() - now.getTimeInMillis();
		this.diff = diffMilliSec / (24 * 60 * 60 * 1000);
	}
	
	public long getDiff() {
		return diff;
	}
	
	public String getDescr() {
		return descr;
	}
	
	public Calendar getCalendar() {
		return data;
	}

	public String toString1() {
		return "ATTENZIONE!! Il " + dateFormat.format(data.getTime()) + " è scaduto: "+ descr + "\tCancellare o modificare la scadenza" + "\n";
	}
	
	public String toString2() {
		return "ATTENZIONE!! Oggi scade: " + descr + "\n";
	}
	
	public String toString3() {
		return "Il " + dateFormat.format(data.getTime()) + " scade: " + descr + "\n";
	}

	public void setData(Calendar data) {
		this.data = data;
	}

}
