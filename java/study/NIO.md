# New Input/Output

* Java 7 로 버전업하면서 IO 와 NIO 사이의 일관성 없는 클래스 설계를 바로 잡고, 비동기 채널 등의 네트워크 지원을 대폭 강화한 NIO.2 API 가 추가되었다.
* Nio2 패키지에 제공되지 않고, 기존 java.nio 하위 패키지에 통합되어 있다.
  1. java.nio.channels
  2. java.nio.charset
  3. java.nio.file

| NIO 패키지                 | 포함되어 있는 내용                            |
| ----------------------- | ------------------------------------- |
| java.nio                | 다양한 버퍼 클래스                            |
| java.nio.channels       | 파일 채널, TCP 채널, UDP 채널 등의 클래스          |
| java.nio.channels.spi   | java.nio.channels 패키지를 위한 서비스 제공자 클래스 |
| java.nio.charset        | 문자셋, 인코더, 디코더 API                     |
| java.nio.charset.spi    | java.nio.charset 패키지를 위한 서비스 제공자 클래스  |
| java.nio.file.attribute | 파일 및 파일 시스템의 속성에 접근하기 위한 클래스          |
| java.nio.file.spi       | java.nio.file 패키지를 위한 서비스 제공자 클래스     |

## IO vs NIO

| 구분            | IO         | NIO    |
| ------------- | ---------- | ------ |
| 입출력 방식        | 스트림 장식     | 채널 방식  |
| 버퍼 방식         | non-buffer | buffer |
| 비동기 방식        | X          | O      |
| 블로킹 / 넌클로킹 방식 | 블로킹만 지원    | 모두 지원  |

## 스트림 vs 채널

* IO 는 스트림 기반이다.
  * 출력과, 입력 스트림이 다르기 때문에 각각 스트림을 따로 생성해야 한다.
* NIO 는 채널 기반이다.
  * 양방향으로 입력과 출력이 가능하다.

## buffer vs non-buffer

* IO 에서 출력 스트림이 1 바이트를 쓰면 입력 스트림은 1 바이트를 읽는다.
* 이는 대체로 느리고, 이로인해 buffer 를 사용해서 복수 개의 바이트를 한번에 입/출력 할 수 있는 방식을 사용할 수 있다.
  * BufferedInputStream / BufferedOutputStream  을 이용할 수 있다.
* NIO 는 기본적으로 buffer 를 사용한다.
* IO 는 스트림에서 읽은 데이터를 즉시 처리한다.
  * 스트림으로부터 입력된 전체 데이터를 별도로 저장하지 않으면, 입력된 데이터의 위치를 이동해 가면서 자유롭게 이용할 수 없다.
* NIO 는 읽은 데이터를 무조건 버퍼에 저장하기 때문에 필요한 부분만 읽고 쓸 수 있다.

## 블록킹 vs 논블록킹

* IO
  * 입력 스트림의 read() 메소드를 호출하면 데이터가 입력되기 전까지 스레드는 블록킹 상태가 된다.
  * 출력 스트림의 write() 메소드를 호출하면 데이터가 출력되기 전까지 스레드는 블록킹된다.
  * 블록킹을 빠져나오려면 interrupt 는 할 수 없고, 스트림을 닫아야만 한다.
* NIO
  * 블록킹과 논블록킹 특징을 모두 가지고 있다.
  * NIO 의 논블로킹은 입출력 작업 준비가 완료된 채널만 선택해서 작업 스레드가 처리하기 때문에 작업 스레드가 블로킹 되지 않는다.
  * NIO 논블로킹의 핵심 객체는 multiplexor 인 selector 이다.
    * selector -> 복수 개의 채널 중에서 준비 완료된 채널을 선택하는 방법을 제공

## IO vs NIO

* NIO 는 불특정 다수의 클라이언트 연결 또는 멀티 파일들을 논블록킹이나 비동기로 처리할 수 있기 때문에 과도한 스레드 생성을 피하고 스레드를 효과적으로 사용할 수 있다는 장점이 존재한다.
* 운영체제의 버퍼를 이용한 입출력이 가능하기 때문에 입출력 성능이 향상된다.
* NIO 는 연결 클라이언트 수가 많고, 하나의 입출력 처리 작업이 오래 걸리지 않는 경우에 사용하는 것이 좋다.
* IO 는 대용량 데이터를 처리할 경우 유리하다.
* NIO 는 버퍼 할당 크기도 문제가 있고, 모든 입출력 작업에 버퍼를 무조건 사용해야 하므로 받는 즉시 처리하는 IO 보다 복잡하다.
* 연결 클라이언트 수가 적고, 전송되는 데이터가 대용량이면서 순차적으로 처리될 필요성이 있을 경우에는 IO 로 서버를 구성하는것이 좋다.

## 파일과 디렉토리

* File 클래스 외에 NIO 파일 관련 java.nio.file, java.nio.attribute 패키지가 존재한다.

### Path

* NIO path 는 java.nio.file.Paths 에서 사용할 수 있다.

> Path path = Paths.get(String first, String... more); <br/>
> get 은 java.nio.file.Paths 의 정적 메소드이다.

* path 에 존재하는 여러 메소드

| 리턴 타입          | 메소드(매개 변수)            | 설명                                                               |
| -------------- | --------------------- | ---------------------------------------------------------------- |
| int            | compareTo(Path other) | 파일 경로가 동일하면 0 <br/> 상위 경로면 음수 <br/>  하위 경로면 양수                   |
| Path           | getFileName()         | 부모 경로를 제외한 파일 또는 디렉토리 이름만 가진 Path 리턴                             |
| FileSystem     | getFileSystem()       | FileSystem 객체 리턴                                                 |
| Path           | getName(int index)    | /path/to/file.txt <br/> 0 : path <br/> 1 : to <br/> 2 : file.txt |
| int            | getNameCOunt()        | 중첩 경로 수 <br/> /path/to.file.txt 이면 3                             |
| Path           | getParent()           | 바로 위 부모 폴더의 Path 리턴                                              |
| Path           | getRoot()             | 루트 디렉토리 리턴                                                       |
| Iterator<Path> | iterator()            | 경로에 있는 모든 디렉토리와 파일을 Path 객체로 생성하고 반복자를 리턴                        |
| Path           | normalize()           | 상대 경로로 표기할 떄 불필요한 요소를 제거                                         |
| WatchKey       | register(...)         | WatchService 를 등록                                                |
| File           | toFile()              | java.io.File 객체로 리턴                                              |
| String         | toString()            | 파일 경로를 문자열로 리턴                                                   |
| URI            | toUri()               | 파일 경로를 URI 객체로 리턴                                                |

### FileSystem

* 운영체제의 파일 시스템은 FileSystem 인터페이스를 통해 접근할 수 있다.

> FileSystem fileSystem = FileSystems.getDefault();

* FileSystem 의 메소드들

| 리턴 타입                | 메소드(매개 변수)           | 설명                            |
| -------------------- | -------------------- | ----------------------------- |
| Iterable\<FileStore> | getFileStores()      | 드라이버 정보를 가진 FileStore 객체들을 리턴 |
| Iterable\<Path>      | getRootDirectories() | 루트 디렉토리 정보를 가진 Path 객체들을 리턴   |
| String               | getSeparator()       | 디렉토리 구분자 리턴                   |

* FileStore 는 드라이버를 표현한 객체이며, 다음과 같은 메소드들이 있다.

| 리턴 타입   | 메소드(매개 변수)            | 설명                                       |
| ------- | --------------------- | ---------------------------------------- |
| long    | getTotalSpace()       | 드라이버 전체 공간 크기를 바이트 단위로 리턴                |
| long    | getUnallocatedSpace() | 할당되지 않은 공간 크기를 바이트 단위로 리턴                |
| long    | getUsableSpace()      | 사용 가능한 공간 크기, getUnallocatedSpace() 와 동일 |
| boolean | isReadOnly()          | 읽기 전용 여부                                 |
| String  | name()                | 드라이버명 리턴                                 |
| String  | type()                | 파일 시스템 종류                                |

### 파일 속성 읽기 및 파일, 디렉토리 생성/삭제

* java.nio.file.Files 클래스는 파일과 디렉토리의 생성 및 삭제, 이들의 속성을 읽는 메소드가 존재한다.
* java.nio.fileFiles 클래스가 정적 메소드로 제공되는 것들이 많다.
* https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html

### WatchService

* Java 7 에서 추가되었으며, 디렉토리 내부에서 파일 생성, 삭제, 수정 등의 내용 변화를 감지하는데 사용한다.
* watchService 객체를 생성한 후, 감시할 디렉토리의 Path 객체를 register() 메소드로 watchService 에 등록해야 한다.
  * 생성, 삭제, 수정 중 감지할 것을 StandardWatchEventKinds 상수로 지정한다.

> WatchService watchService = FileSystems.getDefault().newWatchService(); <br/>
> StandardWatchEventKinds 의 상수값을 준다.<br/>
> path.register(watchService, ~.ENTRY_CREATE, ~.ENTRY_MODIFY, ~.ENTRY_DELETE);

* path 에 register 에 등록된 순간부터 path 의 경로에 감시하는 이벤트가 발생하면 다음과 같은 순서로 동작한다.
  1. WatchEvent 가 발생한다.
  2. WatchService 는 해당 이벤트 정보를 가진 WatchKey 를 생성하여 Queue 에 넣는다.
  3. 프로그램은 무한 루프를 돌면서 WatchService 의 take() 메소드를 호출한다.
  4. WatchKey 가 들어올 때까지 대기하고 있다가 WatchKey 가 큐에 들어오면 WatchKey 를 얻어서 처리한다.

```JAVA
while(true) {
    WatchKey watchKey = watchService.take();
    List<WatchEvent<?> list = watchKey.pollEvents();

    for(WatchEvent watchEven : list) {
        Kind kind = watchEvent.kind();

        Path path = (Path)watchEvent.context();

        if (kind === StandarWatchEventKinds.ENTRY_CREATE) { 생성되었을 경우 }
        else if (kind === StandarWatchEventKinds.ENTRY_DELETE) { 상제되었을 경우 }
        else if (kind === StandarWatchEventKinds.ENTRY_MODIFY) { 변경되었을 경우 }
        else if (kind === StandarWatchEventKinds.OVERFLOW) { 운영체제에서 이벤트가 소실됐거나 버려진 경우 }

        if (!watchKey.reset()) break;
    }
    watchService.close();
}
```

## Buffer

* Buffer - 읽고 쓰기가 가능한 메모리 배열
* NIO 에서 데이터를 입출력하기 위해 항상 버퍼를 사용해야 한다.
* 사용하는 메모리에 따라 Direct/NonDirect 로 분류된다.

### Direct/NonDirect Buffer

| 구분          | NonDirect Buffer | Direct Buffer        |
| ----------- | ---------------- | -------------------- |
| 사용하는 메모리 공간 | JVM 의 힙 메모리      | 운영체제의 메모리            |
| 버퍼 생성 시간    | 빠르다              | 느리다                  |
| 버퍼의 크기      | 작다               | 크다 (큰 데이터를 처리할 때 유리) |
| 입출력 성능      | 낮다               | 높다 (입출력이 빈번할 때 유리)   |

* Direct Buffer 의 경우 생성하는데 오래 걸리므로, 한번 생성하고 재사용하는데 적합하다.
* NonDirect Buffer 의 입출력
  1. 임시 다이렉트 버퍼를 생성하고 NonDirect Buffer 의 내용을 임시 버퍼에 복사
  2. 임시 다이렉트 버퍼를 사용해서 운영체제의 native I/O 기능을 수행
* NonDirect Buffer 의 경우 제한된 JVM 힙 메모리를 사용해서 생성할 때 크기 제한이 있다.

> ByteBuffer nonDirectBuffer = ByteBuffer.allocate(1024 * 1024 * 1024); <br/>
> OutOfmemoryError

* DirectBuffer 는 채널을 사용해서 버퍼의 데이터를 읽고 저장할 경우에만 운영체제의 native I/O 를 수행한다.
  * 채널을 사용하지 않고 get()/put() 메소드를 사용하면 내부적으로 JNI 를 호출하기 때문에, JNI 오버헤드가 추가된다.
  * 즉, 채널을 사용하지 않고 get()/put() 메소드를 사용한다면 NonDirectBuffer 가 더 효율적일 수 있다.

### Buffer 생성

* NonDirectBuffer 는 allocate(), wrap() 메소드를 호출하면 된다.
* DirectBuffer 는 DirectBuffer.allocateDirect() 메소드로 호출하면 된다.
* allocate()
  * JVM 힙 메모리에 넌다이렉트 버퍼를 생성한다.
  * ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShorBuffer 클래스에서 매개변수로 들어간 capacity 크기만큼 할당되어 생성된다.

> XXXBuffer xxxBuffer = XXXBuffer.allocate(100);

* wrap()
  * 이미 생성되어 있는 자바 배열을 래핑하여 Buffer 객체를 생성한다.

> XXXBuffer xxxBuffer = XXXBuffer.wrap(xxxArray); <br/>
> CharBuffer charBuffer = CharBuffer.wrap("스트링은 CharSequenece 인터페이스를 구현했지요."); <br/>
> XXXBuffer xxxBuffer = XXXBuffer.wrap(xxxArray, 0, 50); <br/>
> 인덱스 0 부터 50개만 버퍼로 생성한다.

* allocateDirect()
  * ByteBuffer 에서만 생성이 가능하며, 다른 타입으로 하고 싶다면 asXXXBuffer() 메소드를 사용하여 해당 타입 DirectBuffer 를 얻어야 한다.

### byte 해석 순서

* Big endian
  * 앞쪽 바이트부터 처리
* Little endian
  * 뒤쪽 바이트부터 처리
* 운영체제마다 endian 이 다르다.
* JVM 도 독립된 운영체제이지만, JRE 가 설치되어 있다면 JVM 은 무조건 BIG endian 으로 동작하도록 되어 있다.
* ByteOrder.nativeOrder() 를 통해 현재 운영체제의 endian 을 알 수 있다.
* DirectBuffer 는 운영체제의 native I/O 를 사용하기 때문에, endian 을 세팅해줘야 한다.

> ByteBuffer byteBuffer = ByteBuffer.allocateDirect(someNumber).order(ByteOrder.nativeOrder());

### Buffer 위치 속성

| 속성       | 설명                                                                                                                                                                                                                                           |
| -------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| position | 현재 읽거나 쓰는 위치값. <br/> 인덱스 값이기 때문에 0부터 시작하며, limit 보다 큰 값을 가질 수 없다. <br/> 만약 position 과 limit 의 값이 같아진다면, 더 이상 데이터를 쓰거나 읽을 수 없다.                                                                                                               |
| limit    | 버퍼에서 읽거나 쓸 수 있는 위치의 한계를 나타낸다. <br/> 이 값은 capacity 보다 작거나 같은 값을 가진다. <br/> 최초에 버퍼를 만들었을 때는 capacity 와 같은 값을 가진다.                                                                                                                              |
| capacity | 버퍼의 최대 데이터 개수(메모리 크기)를 나타낸다. <br/> **인덱스 값이 아니라 수량이다.**                                                                                                                                                                                      |
| mark     | reset() 메소드를 실행했을 때 돌아오는 위치를 지정하는 인덱스이며, mark() 메소드로 지정할 수 있다. <br/> 주의할 점은 반드시 position 이하의 값으로 지정해주어야 한다. <br/> position 이나 limit 의 값이 mark 값보다 작은 경우, mark 는 자동 제거된다. <br/> mark 가 없는 상태에서 reset() 메소드를 호출하면 InvalidMarkException 이 발생한다. |

> 0 ≤ mark ≤ position ≤ limit ≤ capacity

### 공통 메소드

* https://docs.oracle.com/javase/7/docs/api/java/nio/Buffer.html
  * index 값의 매개 변수가 없으면, 상대적으로 동작한다.
* 버퍼 예외 종류

| 예외 | 설명 |
| ---- | ---- |
| BufferOverflowException | position 이 limit 에 도달했을 떄 put() 호출하면 발생 |
| BufferUnderflowException | position 이 limit 에 도달했을 때 get() 호출하면 발생 |
| InvalidMarkException | mark 가 없는 상태에서 reset() 메소드를 호출하면 발생 |
| readOnlyBufferException | 읽기 전용 버퍼에서 put() 또는 compact() 메소드를 호출하면 발생 |

### Buffer 변환

* 채널이 데이터를 읽고 쓰는 버퍼는 모두 ByteBuffer 이다.

```JAVA
Charset charset = Charset.forName("UTF-8");
// 문자열 -> 인코딩 -> ByteBuffer
String data = "FOO";
ByteBuffer byteBuffer = charset.encode(data);

// ByteBuffer -> 디코딩 -> CharBuffer -> 문자열
data = charset.decode(byteBuffer).toString();

// ByteBuffer <-> IntBuffer
int[] data = new int[] {10, 20};
IntBuffer intBuffer = IntBuffer.wrap(data);
ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
for(int i = 0; i < intBuffer.capacity(); i++) {
    byteBuffer.putInt(intBuffer.get(i));
}
byteBuffer.flip();
...
IntBuffer intBuffer = byteBuffer.asIntBuffer();
int[] data = new int[intBuffer.capacity()];
intBuffer.get(data);
// ByteBuffer.asIntBuffer() 로 얻은 IntBuffer 는 array() 메소드를 사용해서 int[] 배열을 얻을 수 없다.
// array() 메소드는 래핑한 배열만 리턴하기 때문에 int[] 배열을 wrap() 메소드로 래핑한 IntBuffer 에서만 사용할 수 있다.
```

* asIntBuffer() 처럼 Shor, Int, Long, Float, Double 모두 사용하여 위 ByteBuffer <-> IntBuffer 처럼 할 수 있다.