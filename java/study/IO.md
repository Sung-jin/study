# Input/Output (입출력)

* 자바에서 데이터는 스트림을 통해 입출력 된다.
  * 스트림 - 단일 방향으로 연속적으로 흘러가는 것을 말한다.

* java.io 패키지의 여러 입출력 스트림

| java.io 패키지의 주요 클래스                                                                                                                                                                         | 설명                             |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| File                                                                                                                                                                                        | 파일 시스템의 파일 정보를 얻기 위한 클래스       |
| Console                                                                                                                                                                                     | 콘솔로부터 문자를 입출력하기 위한 클래스         |
| InputStream / OutputStream                                                                                                                                                                  | 바이트 단위 입출력을 위한 최상위 입출력 스트림 클래스 |
| FileInputStream / FileOutputStream <br/> DataInputStream / DataOutputStream <br/> ObjectInputStream / ObjectOupputStream <br/> PrintStream <br/> BufferedInputStream / BufferedOutputStream | 바이트 단위 입출력을 위한 하위 스트림 클래스      |
| Reader / Writer                                                                                                                                                                             | 문자 단위 입출력을 위한 최상위 입출력 스트림 클래스  |
| FileReader / FileWriter <br/> InputStreamReader / OutputStreamWriter <br/> PrintWriter <br/> BufferedReader / BufferedWriter                                                                | 문자 단위 입출력을 위한 하위 스트림 클래스       | D |

* 스트림 클래스는 바이트 기반 클래스와 문자 기반 스트림 2개로 구분된다.
* 바이트 기반 : 그림, 멀티미디어, 문자 등 모든 종류의 데이터를 주고받을 수 있다.
* 문자 기반 : 문자만 주고 받을 수 있도록 특화

| 구분      | 바이트 기반 스트림     |                 | 문자 기반 스트림 |
| ------- | -------------- | --------------- | --------- |
|         | 입력 스트림         | 출력 스트림          | 입력 스트림    | 출력 스트림    |
| 최상위 클래스 | InputStream    | OutputStream    | Reader    | Writer    |
| 하위 클래스  | XXXInputStream | XXXOutputStream | XXXReader | XXXWriter |

## Input Stream

* 바이트 기반 입력 스트림의 최상위 추상 클래스
* 데이터를 입력받을 때의 스트림
* 인풋 스트림의 출발지는 키보드, 파일, 네트워크상의 프로그램 등이 될 수 있다.
* InputStream -> FileInputStream / BufferedInputStream / DataInputStream
* InputStream 클래스에 기본적으로 가지고 있는 메소드

| 리턴 타입 | 메소드                              | 설명                                                                                                     |
| ----- | -------------------------------- | ------------------------------------------------------------------------------------------------------ |
| int   | read()                           | 입력 스트림으로부터 1바이트를 읽고 읽ㄷ은 바이트를 리턴한다.                                                                    |
| int   | read(byte[] b)                   | 입력 스트림으로부터 읽은 바이트들을 매개값으로 주어진 바이트 배열 b 에 저장하고 실제로 읽은 바이트 수를 리턴한다.                                      |
| int   | read(byte[] b, int off, int len) | 입력 스트림으로부터 len 개의 바이트만큼 읽고 매개값으로 주어진 바이트 배열 b[off] 부터 len 개까지 저장한다. 그리고 **실제로 읽은** 바이트 수인 len 개를 리턴한다. |
| void  | close()                          | 사용한 시스템 자원을 반납하고 입력 스트림을 닫는다.                                                                          |

```JAVA
InputStream is = new FileInputStream("some/file/path/test.txt");

int readByte;
while((readByte = is.read() != -1)) { ... }
// read() 메소드

int readByteNo;
byte[] readBytes = new byte[100];
while((readByteNo = is.read() != -1)) { ... }
// 100 개씩 읽어옴

int readByteNo = is.read(readBytes, 200, 100);
// is 에 존재하는 바이트중 200번 인덱스부터 100개를 읽어옴

is.close();
// inputStream 의 자원을 종료
```

## Output Stream

* 바이트 기반 출력 스트림의 최상위 추상 클래스
* 데이터를 보낼 때의 스트림
* 아웃풋 스트림의 도착지는 모니터, 파일, 네트워크상의 프로그램이 될 수 있다.
* OutputStream -> FileOutputStream / PrintStream / BufferedOutputStream / DataOutputStream
* InputStream 클래스에 기본적으로 가지고 있는 메소드

| 리턴 타입 | 메소드                               | 설명                                               |
| ----- | --------------------------------- | ------------------------------------------------ |
| void  | write(int b)                      | 출력 스트림으로 1바이트를 보낸다.                              |
| void  | write(byte[] b)                   | 출력 스트림으로 주어진 바이트 배열 b 의 모든 바이트를 보낸다.             |
| void  | write(byte[] b, int off, int len) | 출력 스트림으로 주어진 바이트 배열 b[off] 부터 len 개까지의 바이트를 보낸다. |
| void  | flush()                           | 버퍼에 잔류하는 모든 바이트를 출력한다.                           |
| void  | close()                           | 사용한 시스템 자원을 반납하고 출력 스트림을 닫는다.                    |

```JAVA
OutputStream os = new FileOutputStream("/some/dist/path/test.txt");

byte[] data = "Hello World".getBytes();
for(s in data) {
    os.write(s); // H e l l o  W o r l d 한개씩 출력
}
os.write(data); //Hello World 모두 출력

os.write(data, 1, 2); //e l 만 출력

os.flush(); // 버퍼에 잔류하는 모든 데이터를 출력시키고 버퍼를 비우는 역할을 한다.
os.close(); // OutputStream 의 자원을 종료
```

## Reader

* 문자 기반 입력 스트림의 최상위 추상 클래스
* Reader -> FileReader / bufferedreader / InputStreamReader
* Reader 클래스에 기본적으로 가지고 있는 메소드

| 리턴 타입 | 메소드                                 | 설명                                                                                    |
| ----- | ----------------------------------- | ------------------------------------------------------------------------------------- |
| int   | read()                              | 입력 스트림으로부터 한 개의 문자를 읽고 리턴한다.                                                          |
| int   | read(char[] cbuf)                   | 입력 스트림으로부터 읽은 문자들을 매개값으로 주어진 문자 배열 cbuf 에 저장하고 실제로 읽은 문자 수를 리턴한다.                     |
| int   | read(char[] cbuf, int off, int len) | 입력 스트림으로부터 len 개의 문자를 읽고 매개값으로 주어진 문자 배열 cbuf[off] 부터 len 개까지 저장하고 실제로 읽은 문자 수를 리턴한다. |
| void  | close()                             | 사용한 시스템 자원을 반납하고 입력 스트림을 닫는다.                                                         |

```JAVA
Reader reader = new FileReader("some/path/file.txt");

int readData;
while((readData = reader.read()) != -1) {
    char charData = (char) readData;
}
// read() 메소드

int readCharNo;
char[] cbuf = new Char[2];
while((readCharNo = reader.read(cbuf)) != -1) { ... }
// 2개 캐릭터씩 읽어옴

char[] cbuf = new char[100];
int readCharNo = reader.read(cbuf, 100, 100);
// 100번 인덱스부터 100개를 읽어옴

reader.close();
// Reader 의 자원을 종료
```
