import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Evento {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private Calendar data;
	private boolean allDay;
	private String descr;
	private long diff;
	private MessaggiManager messaggiManager;
	
	public Evento (Calendar data, boolean allDay, String descr) {
		this.data = data;
		if (allDay) {
			this.data.set(Calendar.HOUR_OF_DAY, 0);
			this.data.set(Calendar.MINUTE, 0);
		}
		this.data.set(Calendar.SECOND, 0);
		this.data.set(Calendar.MILLISECOND, 0);
		this.allDay = allDay;
		this.descr = descr;
		this.diff = calcolaDiff();
		this.messaggiManager = MessaggiManager.getInstance();
	}
	
	public long getDiff() {
		return diff;
	}
	
	public void setDiff(long diff) {
		this.diff = diff;
	}

	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public boolean isAllDay() {
		return allDay;
	}
	
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public long calcolaDiff() {
		Calendar now = new GregorianCalendar();
		now.set(Calendar.HOUR_OF_DAY, this.data.get(Calendar.HOUR_OF_DAY));
		now.set(Calendar.MINUTE, this.data.get(Calendar.MINUTE));
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		long diffMilliSec = this.data.getTimeInMillis() - now.getTimeInMillis();
		return diffMilliSec / (24 * 60 * 60 * 1000);
	}

	public String toString1() {
		if (allDay) {
			return messaggiManager.getMessaggi().getString("attention_on") + dateFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("expired") + descr + messaggiManager.getMessaggi().getString("delete_modify");
		} else {
			return messaggiManager.getMessaggi().getString("attention_on") + dateFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("at") + timeFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("expired") + descr + messaggiManager.getMessaggi().getString("delete_modify");
		}
	}
	
	public String toString2() {
		if (allDay) {
			return messaggiManager.getMessaggi().getString("today_expires") + descr + "\n";
		} else {
			return messaggiManager.getMessaggi().getString("today_at") + timeFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("expired") + descr + "\n";
		}
	}
	
	public String toString3() {
		if (allDay) {
			return messaggiManager.getMessaggi().getString("on") + dateFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("expire") + descr + "\n";
		} else {
			return messaggiManager.getMessaggi().getString("on") + dateFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("at") + timeFormat.format(data.getTime()) + messaggiManager.getMessaggi().getString("expire") + descr + "\n";
		}
	}

}
