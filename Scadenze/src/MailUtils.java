import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean validate(final String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static void inviaMail(String tst) {
		String mittente = "no-reply@scadenze.it";
		String host = "out.alice.it";
		String oggetto = "Scadenza";
		String testo = tst + "<br>Buona giornata &#x263A<br><br><html><font size='2'>Mail inviata automaticamente dal programma scadenze di Andrea Rigoni</font></html>";
		Properties p = new Properties();
		p.put("mail.smtp.host", host);
		p.put("port", 25);
		Session sessione = Session.getDefaultInstance(p);
		MimeMessage mail;
		try {
			for (Email e: EmailManager.getInstance().getEmailList()) {
				mail = new MimeMessage(sessione);
				mail.setFrom(new InternetAddress(mittente));
				mail.addRecipients(Message.RecipientType.TO, e.getEmail());
				mail.setSubject(oggetto);
				mail.setContent(testo, "text/html");
				Transport.send(mail);
			}
		} catch (Exception e) {
			System.out.println("Errore nell'invio dell'email");
			e.printStackTrace();
		}
	}
	
}
