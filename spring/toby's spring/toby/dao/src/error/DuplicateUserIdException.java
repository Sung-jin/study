package error;

public class DuplicateUserIdException extends RuntimeException {
    // 위와 같이 의미있는 언체크드 예외로 처리하여 불필요한 try-catch 블록을 없애고
    // 사용자가 예외 클래스로 어떤 문제인지 파악하기 쉽고,
    // 필요에 따라서 알아서 처리할 수 있도록 하는 형태가 더 좋다
    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
