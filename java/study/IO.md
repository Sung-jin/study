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

## Writer

* 문자 기반 출력 스트림의 최상위 추상 클래스
* Writer -> FileWriter / BufferedWriter / PrintWriter / OutputStreamWriter
* Writer 클래스에 기본적으로 가지고 있는 메소드

| 리턴 타입        | 메소드                                  | 설명                                                 |
| ------------ | ------------------------------------ | -------------------------------------------------- |
| void         | write(int c)                         | 출력 스트림으로 주어진 한 문자를 보낸다.                            |
| void         | write(char[] cbuf)                   | 출력 스트림으로 주어진 문자 배열 cbuf 의 모든 문자를 보낸다.              |
| void         | write(char[] cbuf, int off, int len) | 출력 스트림으로 주어진 문자 배열 cbuf[off] 부터 len 개 까지의 문자를 보낸다. |
| void         | wirte(String str, int off, int len)  | 출력 스트림으로 주어진 문자열 off 순서부터 len 개까지의 문자를 보낸다.        |
| void         | flush()                              | 버퍼에 잔류하는 모든 문자열을 출력한다.                             |
| void close() | 사용한 시스템 자원을 반납하고 출력 스트림을 닫는다.        |

```JAVA
Writer writer = new FileWriter("path/to/dist/test.txt");

char[] data = "FOO".toCharArray();
String str = "FooBar";

for(s in data) writer.write(s);
// 한개씩 출력

wirter.write(data);
// char[] 출력

writer.write(data, 1, 2);
// O O 출력

writer.write(str, 2, 3);
// o B 출력

write.flush();
// 버퍼에 잔류하는 모든 데이터를 출력
wirte.close();
// write 의 자원 해제
```

## 콘솔 입출력

* 시스템을 사용하기 위해 키보드로 입력을 받고 화면으로 출력하는 소프트웨어를 말한다.
* 자바에서 콘솔사용
  1. 입력을 받을때는 System.in
  2. 출력을 할 때 System.out
  3. 에러를 출력할 때는 System.err
* 콘솔에 입출력하고 싶으면 System.in / System.out 을 이용하면 된다.
* Java 6 부터 콘솔에서 입력한 것을 쉽게 읽을 수 있도록 System.io.Console 클래스를 제공한다.
  * Console 은 문자열은 읽을 수 있으나, 정수/실수 등의 기본 타입 값을 바로 읽을 수 없다.
  * java.util 패키지의 Scanner 클래스를 이용하면 기본 값을 바로 읽을 수 있다.

```JAVA
InputStream is = System.in;
int asciiCode = is.read();

////////////////////////
OutputStream os = System.out;
byte b = 97;
os.write(b); // 'a' 출력

////////////////////////
Console console = System.console();

////////////////////////
Scanner scanner = new Scanner(System.in);
```

* 이후 내용들은 input/output stream, writer, reader 를 상속한 클래스들의 사용들이 나온다. 한번 읽어보는거로 해도 될듯.

### 객체 입출력 보조 스트림

* 자바는 메모리에 생성된 객체를 파일 또는 네트워크로 출력할 수 있다.
* 객체는 문자가 아니기 때문에 바이트 기반 스트림으로 출력해야 한다.
* 객체를 출력하기 위해서는 객체의 데이터를 일렬로 늘어선 연속적인 바이트로 변경해야 한다.
  * 연속적인 바이트로 변경하는 것을 serialization 이라 한다.
* 입력 스트림으로부터 읽어들인 연속적인 바이트를 객체로 복원하는 것을 deserialization 이라 한다.
* 객체를 입출력할 수 있는 두 개의 보조 스트림인 ObjectInputStream 과 ObjectOuputStream 을 제공한다.
  * ObjectOutputStream 은 바이트 출력 스트림과 연결되어 객체를 직렬화 하는 역할을 한다.
  * ObjectInputStream 은 바이트 입력 스트림과 연결되어 객체로 역직렬화하는 역할을 한다.

> 바이트 -> InputStream -> ObjectInputStream -> OBJECT -> ObjectOutputStream -> OutputStream -> 바이트

```JAVA
ObjectInputStream ois = new ObjectInputStream(바이트입력스트림);
ObjectOutputStream oos = new ObjectOutputStream(바이트입력스트림);

oos.writeObject(객체);
value = (객체타입) ois.readObject();
```

### Serializable (직렬화가 가능한 클래스)

* 자바는 Serializable 인터페이스를 구현한 클래스만 직렬화할 수 있도록 제한한다.
* Serializable 인터페이스는 객체를 직렬화할 때 private 필드를 포함한 모든 필드를 바이트로 변환해도 좋다는 표시 역할을 한다.
* 객체를 직렬화하면 바이트로 변환되는 것은 필드들이고, 생성자 및 메소드는 직렬화에 포함되지 않는다.
  * 역직렬화할 때에는 필드의 값만 복원된다.
  * 하지만, 필드 선언에 static 또는 transient 가 붙어있을 경우 직렬화가 되지 않는다.

```JAVA
public class Foo implements Serializable {
    public int field1;
    protected int field2;
    int field3;
    private int field4;
    public static int field5;
    transient int field6;
}

// filed1 | field2 | field3 | field4 형태로 일렬로 바이트 데이터로 직렬화된다.
```

### serialVersionUID 필드

* 직렬화된 객체를 역직렬화활 때는 직렬화했을 때와 같은 클래스를 사용해야 한다.
* 클래스의 이름이 같더라도 클래스의 내용이 변경되면 역직렬화는 실패한다.
  * serialVersionUID 가 다르다는 에러가 발생한다.
* serialVersionUID 는 같은 클래스임을 알려주는 식별자 역할을 하며, serializable 인터페이스를 구현한 클래스면 컴파일시 자동으로 serialVersionUID 정적 필드가 추가된다.
  * 클래스를 재컴파일하면 serialVersionUID 의 값이 달라진다.
  * 네트워크를 통해 객체를 직렬화하여 전송하고, 수신쪽에서 재컴파일된 객체를 이용한다면 serialVersionUID 가 달라져서 역직렬화에 실패하게 된다.

### writeObject() / readObject() 메소드

* 두 클래스가 상속 관계에 있을경우, 부모 클래스가 Serializable 인터페이스를 구현하고 잇으면 자식 클래스는 Serializable 인터페이스를 구현하지 않아도 자식 객체를 직렬화하면 부모 필드 및 자식 필드가 모두 직렬화 된다.
* 자식 클래스만 Serializable 인터페이스를 구현하고 부모가 구현하지 않았다면, 부모의 필드는 직렬화에서 제외된다.
  * 부모 클래스가 Serializable 인터페이스를 구현하도록 한다.
  * 자식 클래스에서 writeObject() 와 readObject() 메소드를 선언하여 부모 객체의 필드를 직접 출력시킨다.
* 부모 클래스 변경이 불가능할 때 등의 이유가 있을 때 writeObject()/readObejct() 메소드를 사용한다.
  * 이 메소드들은 직렬화 될 때 - wrtieObject(), 역직렬화 될 때 - readObject() 가 자동으로 호출된다.
* writeObject() 와 readObjecT() 는 접근 제한자가 private 이어야 한다.

## 네트워크

> 클라이언트 -(요청)-> 서버 <br/>
> 클라이언트 <-(응답)- 서버

1. 클라이언트에서 연결 요청
2. 서버에서 연결 수락
3. 클라이언트에서 처리 요청
4. 서버에서 처리
5. 서버에서 처리 결과 응답

### IP 주소와 포트

* Internet Protocol - 컴퓨터에 고유한 주소
* IP 를 통해 서로 다른 네트워크상의 다른 컴퓨터와 통신할 수 있다.
  * DNS 를 통해 연결할 컴퓨터의 IP 를 찾는다.
* IP 는 컴퓨터의 네트워크 어댑터까지만 갈 수 있고, 서버마다 고유한 정보인 포트가 있으며 이 포트를 통해 그 IP 의 특정 프로그램을 찾을 수 있다.
* 포트의 범위는 0~65535 이며, 아래와 같이 정해져 있다.

| 구분명                             | 범위            | 설명                                          |
| ------------------------------- | ------------- | ------------------------------------------- |
| Well Know Port Numbers          | 0 ~ 1023      | 국제인터넷주소관리기구(ICANN) 가 특정 애플리케이션용으로 미리 예약한 포트 |
| Registered Port Numbers         | 1024 ~ 49151  | 회사에서 등록해서 사용할 수 있는 포트                       |
| Dynamic Or Private Port Numbers | 49152 ~ 65535 | 운영체제가 부여하는 동적 포트 또는 개인적인 목적으로 사용할 수 있는 포트   |

### InetAddress 로 IP 주소 얻기

* java.net.InetAddress 객체로 IP 주소를 표현한다.
* 로컬 IP 주소 뿐 아니라, DNS 에서 도메인을 검색하여 IP 주소를 가져오는 기능을 제공한다.

> InetAddress ia = InetAddress.getLocalHost(); <br/>
> InetAddress ia = InetAddress.getLocalHost(String host); <br/>
> InetAddress[] iaArr = InetAddress.getAllByName(String host);

### TCP(Transmission Control Protocol) 네트워킹

* 연결 지향적 프로토콜
  * 클라이언트와 서버가 연결된 상태에서 데이터를 주고받는 프로토콜
  * 클라이언트가 연결 요청을 하고, 서버가 연결을 수락하면 통신 서로가 고정되고, 모든 데이터는 고정된 통신 선로를 통해서 순차적으로 전달된다.
  * 연결된 상태에서 데이터를 주고받아서 안정적으로 전달하지만, 그 과정을 위한 준비과정이 오래 걸린다는 단점이 있다.
* java.net.ServerSocket, java.net.Socket 클래스에서 제공한다.
* ServerSocket 클래스 - 클라이언트의 연결 요청을 기다리면서 연결 수락을 담당
* Socket 클래스 - 연결된 클라이언트와 통신을 담당하는 것
* 서버와 클라이엌트의 연결
  1. 클라이언트가 서버에 접속할 포트가 존재하야 하며, 그 포트에 연결되는 것을 포트 바인딩이라 한다.
     * ServerSocket 을 생성할 때 포트 번호를 하나 지정해준다.
  2. 클라이언트는 서버의 IP 주소와 바인딩 포트 번호로 Socekt 을 생성하여 연결 요청을 한다.
  3. ServerSocket 은 클라이언트가 연결 요청을 해오면 accept() 메소드로 연결 수락하고 통신용 socket 을 생성한다.
  4. 클라이언트와 서버는 연결된 상태에서 각각의 socket 을 이용해서 데이터를 주고 받는다.
* ServerSocket 객체 생성

> ServerSocekt serverSocket1 = new ServerSocket(bind 할 port number); <br/>
> ServerSocekt serverSocket2 = new ServerSocket(); <br/>
> serverSocekt2.bind(new InetSocektAddress(bind 할 port number)); <br/>
> ServerSocekt serverSocket3 = new ServerSocket(); <br/>
> serverSocekt3.bind(new InetSocektAddress(IP, bind 할 port number)); <br/>

* 이미 사용중인 port 를 바인딩하면 BindException 이 발생한다.
* ServerSocket 이 accept() 되기 전까지 블로킹 된다.
* accept() 에서 블록킹 된 상태에서 close() 하면 SocketException 이 발생한다.
* InetSocketAddress 객체의 메소드

| 리턴 타입  | 메소드명(매개 변수)   | 설명                   |
| ------ | ------------- | -------------------- |
| String | getHostName() | 클라이언트 IP 리턴          |
| int    | getPort()     | 클라이언트 포트 번호 리턴       |
| String | toString()    | "IP:포트번호" 형태의 문자열 리턴 |

=====================================================================

* TCP 소켓 연결할 때
  * 클라이언트의 역할 일 경우 Socekt 클래스를 사용
    * 연결 요청이 오면 accept() 메소드로 수락하고 통신 시작
  * 서버에서 연결을 기다리는 경우 SocketServer 클래스를 사용
    * 외부 서버를 연결하고 싶으면, 생성한 socket 객체에 ip (InetSocketAddress 를 통한 도메인) 및 포트번호를 인자값으로 생성
* 연결된 이후 앞에서 배웠던 스트림을 이용하여 각각 통신이 된다.
  * socket 객체의 getInputStream() / getOutputStream() 을 통해 얻는다.
* 소켓 연결을 할 떄에는 accept()/connect()/read()/wirte() 메소드를 각각 별도의 스레드를 생성하여 통신하는게 좋다.
  * 하지만, 수천개의 클라이언트가 동시에 연결될 경우 수천개의 스레드가 생성되어 성능이 저하가 된다.
  * 이를 위해 스레드풀을 사용하여 구현하는게 더 좋다.

### UDP (User Datagram Protocol)

* 비연결 지향적 프로토콜
  * 데이터를 주고받을 때 연결 절차를 거치지 않고, 발신자가 일방적으로 데이터를 발신하는 방식
  * 연결의 절차가 없기에 TCP 보다 빠르지만, 데이터 전달의 신뢰성이 떨어진다.
  * 발송자가 데이터를 순차적으로 발송하여도, 각각 데이터가 전달되는 선로의 상황에 따라 수신자가 데이터를 받는 순서가 다를 수 있으며 데이터가 중간에 누락될 수도 있다.
* 자바에서 UDP 는 java.net.DatagramSocekt / java.net.DatagramPacket 클래스에서 제공하고 있다.

```JAVA
DatagramSocket datagramSocket = new DatagramSocket();
String data = "전송하고 싶은 데이터";
byte[] byteArr = data.getByutes.("UTF-8");
DatagramPacket packet = new DatagramPacket(
    byteArr, byteArr.length, new InetSocketAddress("IP 주소", "포트 주소")
);
datagramSocket.send(packet);
datagramSocket.close();
// 발송자

DatagramSocket datagramSocket = new DatagramSocket("연결을 기다리는 포트 주소");
DatagramPacket datagramPacket = new DatagramPacket(new byte[100], 100);
datagramSocket.receive(datagramPacket);
String data = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
// 수신 받는 형태
SocketAddress socketAddress = packet.getSocektAddress();
// 발신지의 정보를 알아내는 방법
datagramSocekt.cloxse();
```