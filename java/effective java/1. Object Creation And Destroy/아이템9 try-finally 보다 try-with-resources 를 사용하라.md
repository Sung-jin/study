## try-finally 보다 try-with-resources 를 사용하라

* 자바 라이브러리에는 close 메서드를 호출하여 직접 닫아줘야 하는 자원이 많다
* 자원 닫기의 경우 클라이언트가 놓치기 쉬워서 예측할 수 없는 성능 문제로 이어지기도 한다
    * 안전망으로 `finalizer`/`cleaner` 를 사용할 수 있지만, 믿을만한 기능은 아니다

```java
String firstLineOfFile(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
        // 기기의 물리적인 문제가 있다면 readLine 메서드가 예외를 던지고, close 도 실패한다
        // 이때 두번재 예외가 첫번째 예외를 덮어씌우면서 두번째 예외만 남게되어
        // 실제 시스템에서 디버깅을 어렵게 한다
    } finally {
        br.close();
    }
}
// 위와 같이 자원 회수를 finally 에서 처리할 수 있으나,

void copy(String src, String dst) throws IOException {
    InputStream in = new InputStream(src);
    try {
        OutputStream out = new OutputStream(dst);
        try {
            ...
        } finally {
            out.close();
        }
    } finnaly {
        in.close();
    }
}
// 닫아야 할 자원이 2개 이상이면 코드가 많이 지저분해 진다
```

### try-with-resources

* Java7 에서 생긴 문법이며, AutoCloseable 인터페이스를 구현한 객체만 해당 구문을 사용할 수 있다
* 자바 라이브러리와 서드파티 라이브러리들의 많은 클래스와 인터페이스가 이미 AutoCloseable 을 구현하거나 확장하였다
* 즉, 닫아야 하는 클래스를 작성한다면 AutoCloseable 을 반드시 구현하는게 좋다

```java
String firstLineOfFile(String path) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(path));){
        return br.readLine();
    }
}
// 이전 예제와 같이 기기의 물리적인 문제가 발생하여 readLine 과 close 가 둘다 예외가 발생할 상황에
// readLine 의 예외가 기록되고, close 에서 발생한 예외는 ksuppressed 라는 꼬리표를 달고 출력된다
// 이러한 숨겨진 예외는 Throwable 에 추가된 getSuppressed 메서드를 이용하면 프로그램 코드에서 가져올 수 있다

void copy(String src, String dst) throws IOException {
    try (InputStream in = new InputStream(src); OutputStream out = new OutputStream(dst)) {
        ...
    }
}
// 위의 try-finally 보다 훨씬 간결하고 close 에 대한 처리를 별도로 하지 않아도 자동으로 처리를 해준다
```

* try-with-resources 도 catch 절을 사용할 수 있다

## 정리

* 꼭 회수해야 하는 자원을 다룰때는 try-finally 이 아닌 try-with-resources 를 사용하자
* 이는 코드가 더 짧아지고 분명하게 만들어지며, 만들어지는 예외 정보도 훨씬 유용하다
* try-finally 로 작성하면 실용적이지 못할 코드가 지저분해지는 경우라도, try-with-resources 로는 정확하고 쉽게 자원을 회수할 수 있다
