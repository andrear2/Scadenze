import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


public class MessaggiManager {
	private static MessaggiManager instance = null;
	private Locale locale;
	private ResourceBundle messaggi;
	
	private static final String MESSAGGI = "properties/MessagesBundle";
	
	private MessaggiManager() {
	}
	
	public static MessaggiManager getInstance() {
		if (instance == null) {
			instance = new MessaggiManager();
			instance.setLocale(new Locale("it", "IT"));
		}
		return instance;
	}

	public ResourceBundle getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(ResourceBundle messaggi) {
		this.messaggi = messaggi;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
		messaggi = ResourceBundle.getBundle(MESSAGGI, locale);
		JOptionPane.setDefaultLocale(locale);
	}
	
}
