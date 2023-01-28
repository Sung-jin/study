package bean;

public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Message newMessage(String text) {
        return new Message(text);
        // 스프링은 private 생성자를 가진 클래스를 빈으로 등록하면 리플렉션을 이용하여 오브젝트를 만든다
        // 이는 일부러 private 생성자로 설정한 부분을 무시하는 것이므로 강제로 생성할 경우 문제가 발생할 수 있다
        // 즉, private 생성자만 가진 클래스를 빈으로 등록하는 행위는 권장되지 않는다
    }
}
