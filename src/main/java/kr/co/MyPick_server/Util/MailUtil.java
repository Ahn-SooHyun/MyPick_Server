package kr.co.MyPick_server.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MailUtil {

    public void sendEmail(JavaMailSender mailSender, String fromEmail, String fromName, String to, String subject, String text) {
        try {
            // MimeMessage 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 발신자 이름과 이메일 설정
            helper.setFrom(fromEmail, fromName); // 발신자 이메일 및 이름 설정
            helper.setTo(to); // 수신자 이메일 설정
            helper.setSubject(subject); // 제목 설정
            helper.setText(text, true); // 내용 설정 (HTML 여부: true)

            // 이메일 발송
            mailSender.send(message);

        } catch (MessagingException e) {
            // 예외 발생 시 로깅 또는 처리
            e.printStackTrace();
            // 예외를 다시 던지거나 처리하지 않을 경우 로그만 남길 수 있음
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
