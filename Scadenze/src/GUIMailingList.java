import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIMailingList extends JFrame {
	private static final long serialVersionUID = 205865826805851927L;
	
	private JComboBox<String> lista;
	private JButton cancella, inserisci;
	private JTextField input;
	private MessaggiManager messaggiManager;
	
	public GUIMailingList() {
		messaggiManager = MessaggiManager.getInstance();
		
		EmailManager emailManager = EmailManager.getInstance();
		emailManager.ordinaEmail();
		
		setTitle(messaggiManager.getMessaggi().getString("mailing_list"));
		setSize(600, 150);	
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		lista = new JComboBox<String>(emailManager.getEmail());
		p.add(lista);
		cancella = new JButton(messaggiManager.getMessaggi().getString("delete"));
		if (emailManager.getEmailList().size() == 0) {
			cancella.setEnabled(false);
		}
		p.add(cancella);
		this.add(p, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel(messaggiManager.getMessaggi().getString("insert_email"));
		p2.add(l2);
		input = new JTextField(20);
		p2.add(input);
		inserisci = new JButton(messaggiManager.getMessaggi().getString("insert")); 
		p2.add(inserisci);
		this.add(p2, BorderLayout.SOUTH);
		
		cancella.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				EmailManager emailManager = EmailManager.getInstance();
				
				String daCancellare = lista.getSelectedItem().toString();
				if (emailManager.rimuoviEmail(daCancellare)) {
					FileUtils.scriviEmailList();
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("email_deleted"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.OK_CANCEL_OPTION);
				}
				lista.removeItem(daCancellare);
				if (emailManager.getEmailList().size() == 0) {
					cancella.setEnabled(false);
				}
			}
		});
		
		inserisci.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				EmailManager emailManager = EmailManager.getInstance();
				
				String email = input.getText();
				if(email.trim().equals("")) {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("no_email"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (emailManager.emailGiaInserita(email)) {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("email_duplicated"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!MailUtils.validate(email)) {
					JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("invalid_email"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				emailManager.aggiungiEmail(email);
				emailManager.ordinaEmail();
				FileUtils.scriviEmail(email);
				JOptionPane.showMessageDialog(null, messaggiManager.getMessaggi().getString("email_added"), messaggiManager.getMessaggi().getString("attention"), JOptionPane.INFORMATION_MESSAGE);
				lista.setModel(new DefaultComboBoxModel<String>(emailManager.getEmail()));
				cancella.setEnabled(true);
				input.setText("");
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
}
