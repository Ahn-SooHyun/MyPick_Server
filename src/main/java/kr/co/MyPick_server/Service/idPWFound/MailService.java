package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.Util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements MailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailUtil mailUtil;

    private final String FROM_EMAIL = "your-email@gmail.com"; // 발신자 이메일 설정
    private final String FROM_NAME = "MyPick"; // 발신자 이름


    @Override
    public void sendVerificationCodeEmail(MailSendReq mailSendReq) {
        String subject = "[MyPick] Password Reset Verification Code"; // 제목 설정
        String message = "<html>" +
                "<body>" +
                "<p>Hello,</p>" +
                "<p>This is an email from MyPick. Your verification code is: <strong>" + mailSendReq.getCode() + "</strong></p>" +
                "<p>Please use this code to reset your password. This code is valid for 10 minutes.</p>" +
                "<p>If you did not request a password reset, please ignore this email.</p>" +
                "<br>" +
                "<p>Thank you,</p>" +
                "<p>MyPick Team</p>" +
                "</body>" +
                "</html>";

        mailUtil.sendEmail(mailSender, FROM_EMAIL, FROM_NAME, mailSendReq.getId(), subject, message);

    }
}
