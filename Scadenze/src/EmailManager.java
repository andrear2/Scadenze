import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmailManager {
	
	private static EmailManager instance = null;
	private List<Email> emailList = new ArrayList<Email>();
	
	private EmailManager() {
	}
	
	public static EmailManager getInstance() {
		if (instance == null) {
			instance = new EmailManager();
			FileUtils.leggiEmail();
		}
		return instance;
	}

	public List<Email> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<Email> emailList) {
		this.emailList = emailList;
	}
	
	public String[] getEmail() {
		String[] email = new String[emailList.size()];
		int i = 0;
		for (Email e : emailList) {
			email[i] = e.getEmail();
			i++;
		}
		return email;
	}
	
	public void ordinaEmail() {
		Comparator<Email> cmp = new Comparator<Email>() {
			public int compare(Email e1, Email e2) {
				if (e1.getEmail().compareTo(e2.getEmail()) < 0) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		Collections.sort(emailList, cmp);
	}
	
	public void aggiungiEmail(String email) {
		emailList.add(new Email(email));
	}
	
	public boolean rimuoviEmail(String email) {
		for (Email e: emailList) {
			if (e.getEmail().compareTo(email) == 0) {
				emailList.remove(e);
				return true;
			}
		}
		return false;
	}
	
	public boolean emailGiaInserita (String email) {
		for (Email e: emailList) {
			if (e.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

}
