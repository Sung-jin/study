package service;

public class DummyMailSender implements MailSender {
    public void send(SimpleMailMessage mailMessage) throws MailException {}
    public void send(SimpleMailMessage[] mailMessage) throws MailException {}
}
