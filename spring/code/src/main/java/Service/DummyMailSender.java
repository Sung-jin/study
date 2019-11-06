package Service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
    // 메일 전송 기능의 test stub
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
    }

    public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {
    }
}
