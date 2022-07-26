## use 를 사용하여 리소스를 닫아라

* 더이상 필요하지 않을 때 close 메서드를 사용하여 명시적으로 닫아야 하는 리소스가 존재한다
    * InputStream/OutputStream/Connection/FileReader/BufferedReader/Scanner...
* 이러한 리소스들은 AutoCloseable 을 상속받는 Closeable 인터페이스를 구현하고 있다
* 이러한 리소스들은 최종적으로 리소스에 대한 레퍼런스가 없어질 때 가비지 컬렉터가 처리하지만, 굉장히 느리며 리소스를 유지하는 동안 비용이 많이 든다
    * 따라서 명시적으로 close 메서드를 호출해 주는 것이 좋다
    
### 전통적인 try-finally

```kotlin
fun countCharactersInFile(path: String): Int {
    val reader = BufferedReader(FileREader(path))
    try {
        return reader.lineSequence().sumBy { it.length }
    } finally {
        reader.close()
    }
}
```

* 위와 같은 형태로 리소스에 대한 close 를 처리할 수 있으나, 복잡하고 좋은 방법은 아니다
    * 자원을 사용하거나 닫을 때 예외가 발생할 수 있으며, 위의 예제에서는 예외에 대한 처리가 없다
    * 또한 try-finally 블록 내부중 오류가 발생하면 둘 중 하나만 전파된다
    
### use

* try-finally 와 같이 일반적인 구현이고 많이 사용되므로 표준 라이브러리에 use 라는 이름의 함수로 포함되어 있다

```kotlin
fun countCharactersInFile(path: String): Int {
    BufferedReader(FileREader(path)).use {
        return reader.lineSequence().sumBy { it.length }
    }
    
    File(path).useLines { lines ->
        // 코틀린 표준 라이브러리는 파일 한 줄씩 처리할 때 활용할 수 있는 useLines 메서드도 제공한다
        // 위와 같이 처리하면 메모리에 파일의 내용을 한줄씩만 유지되므로 대용량 파일도 적절하게 처리할 수 있다
        // 하지만 파일의 한 줄을 한번만 사용할 수 있는 단점이 존재한다
        // 파일의 특정 줄을 두번 이상 반복 처리하면 두번 이상 파일을 열어야 한다
        return lines.sumBy { it.length }
    }
}
```

## 정리

* use 를 사용하면 Closeable/AutoCloseable 을 구현한 객체를 쉽고 안전하게 처리할 수 있다
* 파일을 처리할 때는 파일을 한 줄씩 읽어 들이는 useLines 를 사용하는 것이 좋다

