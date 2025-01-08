package kr.co.MyPick_server.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MailUtil {

    /**
     * Sends an email with the specified details.
     *
     * @param mailSender The JavaMailSender instance used to send the email.
     * @param fromEmail  The sender's email address.
     * @param fromName   The sender's name to display in the email.
     * @param to         The recipient's email address.
     * @param subject    The subject of the email.
     * @param text       The body of the email. This can include HTML content if needed.
     */
    public void sendEmail(JavaMailSender mailSender, String fromEmail, String fromName, String to, String subject, String text) {
        try {
            // Create a MimeMessage object for the email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Set sender's email address and display name
            helper.setFrom(fromEmail, fromName);

            // Set recipient's email address
            helper.setTo(to);

            // Set email subject
            helper.setSubject(subject);

            // Set email content (HTML supported if true)
            helper.setText(text, true);

            // Send the email
            mailSender.send(message);

        } catch (MessagingException e) {
            // Handle messaging exceptions (e.g., incorrect email address or mail server issues)
            e.printStackTrace();
            // Optionally rethrow or log the exception
        } catch (UnsupportedEncodingException e) {
            // Handle exceptions related to unsupported character encoding
            throw new RuntimeException(e);
        }
    }
}
