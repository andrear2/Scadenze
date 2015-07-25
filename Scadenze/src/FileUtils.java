import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FileUtils {
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static void creaFile(String filename) {
		File file = new File(filename);
		try {
			// se il file non esiste, verrà creato
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("Errore nella creazione del file " + filename);
			e.printStackTrace();
		}
	}
	
	public static void leggiScadenze() {
		EventiManager eventiManager = EventiManager.getInstance();
		try {
			BufferedReader input = new BufferedReader(new FileReader(Scadenze.SCADENZE));
			String line;
			while ((line = input.readLine()) != null) {
				try {
					String[] str = line.split(";");
					Calendar data = new GregorianCalendar();
					data.setTime(dateFormat.parse(str[0]));
					boolean allDay = Boolean.parseBoolean(str[1]);
					String descrizione = str[2];
					eventiManager.aggiungiEvento(data, allDay, descrizione);
				} catch (Exception e) {
					System.err.println("Errore nella lettura della data nella riga: " + line);
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void leggiEmail() {
		try {
			BufferedReader input = new BufferedReader(new FileReader(Scadenze.EMAIL));
			String line;
			while ((line = input.readLine()) != null) {
				EmailManager.getInstance().aggiungiEmail(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void scriviScadenza(Date data, boolean allDay, String descr) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(Scadenze.SCADENZE, true));
			output.println(dateFormat.format(data) + ";" + allDay + ";" + descr);
			output.close();
		} catch (IOException e) {
			System.err.println("Errore nella scrittura della scadenza: " + descr);
			e.printStackTrace();
		}
	}
	
	public static void scriviScadenze() {
		EventiManager eventiManager = EventiManager.getInstance();
		List<Evento> eventi = eventiManager.getEventi();
				
		String[] daCopiare = new String[eventi.size()];
		int i = 0;
		for (Evento e: eventi) {
			daCopiare[i] = dateFormat.format(e.getData().getTime()) + ";" + e.isAllDay() + ";" + e.getDescr();
			i++;
		}
		try {
		      PrintStream fileOut = new PrintStream(new FileOutputStream(Scadenze.SCADENZE));
		      for (i = 0; i < eventi.size(); i++) {
		    	  fileOut.println(daCopiare[i]);	
		      }
		} catch (IOException e) {
		      System.out.println("Errore nella scrittura del file: " + e);
		}
	}
	
	public static void scriviEmail(String email) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(Scadenze.EMAIL, true));
			output.println(email);
			output.close();
		} catch (IOException e) {
			System.err.println("Errore nella scrittura dell'email: " + email);
			e.printStackTrace();
		}
	}
	
	public static void scriviEmailList() {
		EmailManager emailManager = EmailManager.getInstance();
		List<Email> email = emailManager.getEmailList();
				
		String[] daCopiare = new String[email.size()];
		int i = 0;
		for (Email e: email) {
			daCopiare[i] = e.getEmail();
			i++;
		}
		try {
		      PrintStream fileOut = new PrintStream(new FileOutputStream(Scadenze.EMAIL));
		      for (i = 0; i < email.size(); i++) {
		    	  fileOut.println(daCopiare[i]);	
		      }
		} catch (IOException e) {
		      System.out.println("Errore nella scrittura del file: " + e);
		}
	}
	
}
