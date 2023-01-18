package service;

/**
 * 테스트 대상이 되는 오브젝트의 기능에만 충실하게 수행하면서 빠르게 테스트를 실행할 수 있도록 사용하는 오브젝트를 테스트 대역(test double) 이라고 한다
 * 대표적인 테스트 대역은 테스트 스텁(test stub) 이다
 * 테스트 스텁은 테스트 대상 오브젝트의 의존객체로서 존재하면서 테스트 동안에 코드가 정상적으로 수행할 수 있도록 돕는 것을 말한다
 * 메소드를 통해 전달하는 파라미터가 아닌 테스트 코드 내부에서 간접적으로 이용되며, 이는 DI 등을 통해 미리 의존 오브젝트를 테스트 스텁으로 변경해야 한다
 * DummyMailSender 의 경우 가장 단순하고 심플한 테스트 스텁의 예이다
 */
public class DummyMailSender implements MailSender {
    public void send(SimpleMailMessage mailMessage) throws MailException {}
    public void send(SimpleMailMessage[] mailMessage) throws MailException {}
}
