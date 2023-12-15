import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class newEmail {

    public static void main(String[] args){
        sendEmail("receiver@example.com");
    }

    public static void sendEmail(String email){
        String username = "backups202324@gmail.com";
        String password = "whgo yqcl sspl uxoy";

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Enable debugging logs
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Backup");
            message.setText("Your backup has been done successfully!!");

            Transport.send(message);

            System.out.println("Email sent successfully to: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
