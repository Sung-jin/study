# Stream (스트림)

* Java 8 부터 추가된 배열을 포함한 컬렉션의 저장 요소를 하나씩 참조해서 함수적 스타일로 처리할 수 있도록 해주는 반복자이다.
* Java 7 이전에는 List 의 컬렉션에서 요소를 순차적으로 처리하기 위해서 Iterator 반복자를 사용하였다.

```JAVA
List<String> list = Array.asList("Foo", "Bar", "FooBar");
Iterator<String iterator = list.iterator();
while(iterator.hasNext()) print(iterator.next());

/////////////////////////////////////////////

List<String> list = Array.asList("Foo", "Bar", "FooBar");
Stream<String> stream = list.stream();
stream.forEach( name -> print(name) );
```

## 스트림의 특징

1. 람다식으로 요소 처리 코드를 제공한다.
   * stream 이 제공하는 대부분의 요소 처리 메소드는 함수적 인터페이스 매개 타입을 가지기 때문에 람다식 또는 메소드 참조를 이용해서 요소 처리 내용을 매개값으로 전달할 수 있다.
2. 내부 반복자를 사용하여 병렬 처리가 쉽다.
   * 외부 반복자 - 개발자가 코드로 직접 컬렉션의 요소를 반복해서 가져오는 코드 패턴
     * index 를 이용하는 for, Iterator 를 이용하는 while
   * 내부 반복자 - 컬렉션 내부에서 요소들을 반복시키고, 개발자는 요소당 처리해야 할 코드만 제공하는 코드 패턴
     * 컬렉션 내부에서 어떻게 요소를 반복시킬 것인가는 컬렉션에게 맡겨두고, 개발자는 요소 처리 코드에만 집중할 수 있게 해준다.
     * 반복 순서를 변경하거나, 멀티 코어 CPU 를 최대한 활용하기 위해 요소들을 분배시켜 병렬작업을 할 수 있다.
   * 스트림은 람다식으로 요소 처리 내용만 전달할 뿐, 반복은 컬렉션 내부에서 일어나므로 코드도 간결해지고, 요소의 병렬 처리가 컬렉션 내부에서 처리되므로 여러 이점이 있다.
     * 병렬처리 - 한 가지 작업을 서브 작업으로 나누고, 서브 작업들은 분리된 스레드에서 병렬적으로 처리하는 것을 말한다.
     * 즉, 런타임에 하나의 작업을 서브 작업으로 자동으로 나누고, 서브 작업의 결과를 자동으로 결합해서 최종 결과물을 생성한다.

```JAVA
public static void main(String args[]) {
    public void parallelTest() {
        List<String> list = Arrays.asList("foo", "bar", "dfs", "baz", "qux");

        Stream<String> sequentialStream = list.stream();        // 순차 처리 스트림
        Stream<String> parallelStream = list.parallelStream();  // 병렬 처리 스트림

        sequentialStream.forEach(ParallelExample::print);
        parallelStream.forEach(ParallelExample::print);
    }

    private static void print(String str) {
        System.out.println(str + " : " + Thread.currentThread().getName());
    }
}
```

![lsit_compare](./images/stream_parallel.png)

3. 중간 처리와 최종 처리 작업을 할 수 있다
    * 중간 처리 -> 매핑, 필터링, 정렬을 수행할 수 있다.
    * 최종 처리 -> 반복, 카운팅, 평균, 총합 등의 집계 처리가 가능하다.

```JAVA
public static void main(String args[]) {
    List<Student> studuents = Arrays.asList(
        new Student("Foo", 20),
        new Student("Bar", 40),
        new Student("Baz", 30)
    );

    double avg = studuents
                    .stream()
                    .mapToInt(Student::getScore)
                    .average()
                    .getAsDouble();

    print(avg); // 30.0
}
```

## 스트림의 종류

* BaseStream 인터페이스를 부모로하여 여러 자식 인터페이스들이 존재한다.
  * 모든 스트림에서 공통으로 쓰이는 메소드들이 정의되어 있을 뿐, 직접 사용되지 않는다.
* 종류
  1. Stream - 객체 요소를 처리하는 스트림
  2. IntStream - int
  3. LongStream - long
  4. DoubleStream - double 을 처리하는 스트림

* 스트림을 얻을 수 있는 방법

| 리턴 타입                                                          | 메소드(매개변수)                                                                                                                                                                                            | 소스      |
| -------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| Stream\<T>                                                     | java.utill.Collection.stream() <br/> java.utill.Collection.parallelStream()                                                                                                                          | 컬렉션     |
| Stream\<T> <br/> IntStream <br/> LongStream <br/> DoubleStream | Arrays.stream(T[]), Stream.of(T[]) <br/> Arrays.stream(Int[]), IntStream.of(Int[]) <br/> Arrays.stream(Long[]), LongStream.of(Long[]) <br/> Arrays.stream(Double[]), DoubleStream.of(Double[]) <br/> | 배열      |
| IntStream                                                      | IntStream.range(int, int) <br/> IntStream.rangeClosed(int, int)                                                                                                                                      | int 범위  |
| LongStream                                                     | LongStream.range(long, long) <br/> LongStream.rangeClosed(long, long)                                                                                                                                | long 범위 |
| Stream\<Path>                                                  | Files.find(Path, int, BiPredicate, FileVistOption) <br/> Files.list(Path)                                                                                                                            | 디렉토리    |
| Stream\<String>                                                | Files.lines(Path, Charset) <br/> BufferedReader.lines()                                                                                                                                              | 파일      |
| DoubleStream <br/> IntStream <br/> LongStream                  | Random.doubles(...) <br/> Random.ints(...) <br/> Random.longs(...)                                                                                                                                   | 랜덤 수    |

```JAVA
List<T> list = Arrays.asList(...);
Stream<T> stream = list.stream();
// 컬렉션으로 스트림 얻기

T[] array = {...};
Stream<T> stream = Arrays.strea(array);
// 배열로 스트림 얻기

IntStream stream = IntStream.rangeClosed(1, 100);
System.out.println(stream.sum()); // 5050
// 숫자 범위 스트림 얻기

Path path = Paths.get("some/path/test.txt");
Stream<String> stream = Files.lines(path, Charset.defaultCharset());
// 경로로부터 파일을 얻어서, 그 파일을 라인별로 읽어들인 스트림 얻기

File file = path.toFile();
FileReader fileReader - new FileReader(file);
BufferedReader br = new BufferedReader(fileReader);
stream = br.lines();
// buffer reader 얻기

Path path = Paths.get("some/direcory/path");
Stream<Path> stream = Files.list(path);
// 디렉토리 스트림 얻기
```

## Stream Pipeline

* 대량의 데이터를 가공하여 축소하는 것을 일반적으로 리덕션이라 한다.
* 컬렉션의 요소를 리덕션의 결과물로 바로 잡계할 수 없을 경우에 집계하기 좋도록 필터링, 매핑, 정렬, 그룹핑 등의 중간 처리가 필요하다.

### 중간 처리와 최종 처리

* 데이터의 필터링, 매핑, 정렬, 그룹핑 등의 중간 처리와 합계, 평균, 카운팅, 최대값, 최소값 등의 최종 처리를 파이프라인으로 해결한다.
  * 파이프라인은 여러개의 스트림이 연결되어 있는 구조를 말한다.
  * 최정 처리를 제외하고 모든 스트림은 중간 처리 스트림이다.
* 중간 스트림이 생성될 떄 요소들이 바로 중간 처리되는 것이 아니라 최종 처리가 시작되기 전까지 중간 처리는 지연된다.
* 최종 처리가 시작되면 컬렉션의 요소가 하나씩 중간 스트림에서 처리되고 최종 처리까지 오게 된다.
* 파이프라인 스트림 처리 방식
  1. 스트림 인터페이스에는 많은 중간처리 메소드가 있는데, 이 메소드들은 중간 처리된 스트림을 리턴한다.
  2. 이 스트림에서 다시 중간 처리 메소드를 호출해서 파이프라인을 형성한다.
  3. 최종 스트림까지 1,2 를 반복하고 최종 스트림에 의해 처리된 값을 리턴한다.

```JAVA
double ageAve = list1.stream()
                    .filter(m -> m.getSex() == Member.MALE)
                    .mapToInt(Member::getAge)
                    .average()
                    .getAsDouble();

// average() 메소드는 스트림의 평균을 OptionalDouble 에 저장한다.
// OptionalDouble 에서 값을 읽으려면 getAsDouble() 메소드를 호출해야 한다.
```

### 중간 처리 메소드와 최종 처리 메소드

| 종류    |     | 리턴 타입             | 메소드(매개 변수)           | 소속된 인터페이스                           |
| ----- | --- | ----------------- | -------------------- | ----------------------------------- |
| 중간 처리 | 필터링 | Stream            | distinct()           | 공통                                  |
|       |     | IntStream         | filter(...)          | 공통                                  |
|       | 매핑  | LongStream        | flatMap(...)         | 공통                                  |
|       |     | DoubleStream      | flatMapToDouble(...) | Stream                              |
|       |     |                   | flatMapToInt(...)    | Stream                              |
|       |     |                   | flatMapToLong(...)   | Stream                              |
|       |     |                   | map(...)             | 공통                                  |
|       |     |                   | mapToDouble(...)     | Stream, IntStream, LongStream       |
|       |     |                   | mapToInt(...)        | Stream, LongStream, DoubleStream    |
|       |     |                   | mapToLong(...)       | Stream, IntStream, DoubleStream     |
|       |     |                   | mapToObj(...)        | IntStream, LongStream, DoubleStream |
|       |     |                   | asDoubleStream()     | IntStream, LongStream               |
|       |     |                   | asLongStream()       | IntStream                           |
|       | 정렬  |                   | sorted(...)          | 공통                                  |
|       | 루핑  |                   | peek(...)            | 공통                                  |
| 최종 처리 | 매칭  | boolean           | allMatch(...)        | 공통                                  |
|       |     | boolean           | anyMatch(...)        | 공통                                  |
|       |     | boolean           | noneMatch(...)       | 공통                                  |
|       | 집계  | long              | count()              | 공통                                  |
|       |     | long              | count()              | 공통                                  |
|       |     | OptionalXXX       | findFirst()          | 공통                                  |
|       |     | OptionalXXX       | mat()                | 공통                                  |
|       |     | OptionalXXX       | min()                | 공통                                  |
|       |     | OptionalDouble    | average()            | IntStream, LongStream, DoubleStream |
|       |     | OptionalXXX       | reduce()             | 공통                                  |
|       |     | int, long, double | sum()                | IntStream, LongStream, DoubleStream |
|       | 루핑  | void              | forEach(...)         | 공통                                  |
|       | 수집  | R                 | collect(...)         | 공통                                  |

### 필터링 (distinct(), filter())

* 모든 스트림이 가지고 있는 공통 메소드이다.
* distinct() 메소드는 Object.equal(object) 가 true 이면 동일한 객체로 판단하여 제거한다.
* filter() 는 메소드의 매개값으로 주어진 Predicate 가 true 를 리턴하는 요소만 필터링한다.

| 리턴 타입        | 메소드(매개 변수)              | 설명     |
| ------------ | ----------------------- | ------ |
| Stream       | distinct()              | 중복 제거  |
| IntStream    | filter(Predicate)       | 조건 필터링 |
| LongStream   | filter(IntPredicate)    |        |
| DoubleStream | filter(LongPredicate)   |        |
|              | filter(DoublePredicate) |        |

```JAVA
List<String> list = Arrays.asList("Foo", "Bar", "Foo", "FooBar");
list.stream()
    .distinct()
    .filter(s -> s.startWith("F"))
    .forEach(s -> print(s));
// Foo Bar FooBar
```

### 매핑 (flatMapXXX(), mapXXX(), asXXXStream(), boxed())

* 중간 처리 기능으로 스트림의 요소를 다른 요소로 대체하는 작업을 말한다.

#### flatMapXXX()

* 요소를 대체하는 복수 개의 요소들로 구성된 새로운 스트림을 리턴한다.

| 리턴 타입        | 메소드(매개 변수)                                 | 요소 -> 대체 요소            |
| ------------ | ------------------------------------------ | ---------------------- |
| Stream\<R>   | flatMap(Function<T, Stream\<R>>)           | T -> Stream\<R>        |
| DoubleStream | flatMap(DoubleFunction\<DoubleStream>)     | double -> DoubleStream |
| IntStream    | flatMap(IntFunction\<IntStream>)           | int -> IntStream       |
| LongStream   | flatMap(LongFunction\<LongStream>)         | long -> LongStream     |
| DoubleStream | flatMapToDouble(Function<T, DoubleStream>) | T -> DoubleStream      |
| IntStream    | flatMapToInt(Function<T, IntStream>)       | T -> IntStream         |
| LongStream   | flatMapToLong(Function<T, LongStream>)     | T -> LongStream        |

```JAVA
List<String> list = Arrays.asList("ABC DEF", "GHI JKL");
list.stream()
    .flatMap(data -> Arrays.stream(data.splt(" ")))
    .forEach(data -> print(data));
// ABC DEF GHI JKL
```

#### mapXXX()

* 요소를 대체하는 요소로 구성된 새로운 스트림을 리턴한다.

| 리턴 타입        | 메소드(매배 변수)                        | 요소 -> 대체 요소      |
| ------------ | --------------------------------- | ---------------- |
| Stream\<R>   | map(FUnction<T, R>)               | T -> R           |
| DoubleStream | mapToDouble(ToDoubleFunction\<T>) | T -> double      |
| IntStream    | mapToInt(ToIntFunction\<T>)       | T -> int         |
| LongStream   | mapToLong(ToLongFunction\<T>)     | T -> long        |
| DoubleStream | map(DoubleUnaryOperator)          | double -> double |
| IntStream    | mapToInt(DoubleToIntFunction)     | double -> int    |
| LongStream   | mapToLong(DoubleToLongFunction)   | double -> long   |
| Stream\<U>   | mapToObj(DOubleFunction\<U>)      | double -> U      |
| IntStream    | map(IntUnaryOperator)             | int -> int       |
| DoubleStream | mapToDouble(IntToDoubleFunction)  | int -> double    |
| LongStream   | mapToLong(IntToLongFunction)      | int -> long      |
| Stream\<U>   | mapToObj(IntFunction\<U>)         | int -> U         |
| LongStream   | map(LongUnaryOperator)            | long -> long     |
| IntStream    | mapToInt(LongToIntFunction)       | long -> int      |
| DoubleStream | mapToDouble(LongToLongFunction)   | long -> double   |
| Stream\<U>   | mapToObj(LongFunction\<U>)        | long -> U        |

```JAVA
List<String> list = Arrays.asList("1", "2", "3", "4");
int result = list.stream()
    .map(data -> Integer.parseInt(data)))
    .sum();
print(result); // 10
```

#### asDoubleStream(), asLongStream(), boxed()

* 각 메소드에 적혀있는 숫자타입 외의 스트림을 메소드에 적혀있는 숫자 타입 스트림으로 타입 변환을 한다.
* boxed() 메소드는 int, long, double -> Integer, Long, Double 요소로 박싱하여 stream 을 생성한다.

| 리턴 타입                                                      | 메소드(매개 변수)     | 설명                                                       |
| ---------------------------------------------------------- | -------------- | -------------------------------------------------------- |
| DoubleStream                                               | asDoubleStream | int -> double <br/> long -> double                       |
| LongStream                                                 | asLongStream() | int -> long                                              |
| Stream\<Integer> <br/> Stream\<Long> <br/> Stream\<Double> | boxed()        | int -> Integer <br/> long -> Long <br/> double -> Double |

```JAVA
int[] intArray = {1, 2, 3, ,4, 5};
IntStream intStream = Arrays.stream(intArray);

intStream.asDoubleStream().forEach(d -> print(d));
// 1.0 2.0 3.0 4.0 5.0
```

#### 정렬 (sorted())

* 중간 단계에서 요소를 정렬하여 요소들의 위치를 변경할 수 있다.
* 객체 요소를 정렬할 때는 Comparable 을 구현하지 않으면 ClassCastException 예외가 발생한다.
* 기본 정렬은 오름차순 이므로, 내림차순을 위한 Comparator 가 존재한다.

| 리턴 타입        | 메소드(매개 변수)             | 설명                          |
| ------------ | ---------------------- | --------------------------- |
| Stream\<T>   | sorted()               | 객체를 Comparable 구현 방법에 따라 정렬 |
| Stream\<T>   | sorted(Comparator\<T>) | 객체를 주어진 Comparator 에 따라 정렬  |
| DoubleStream | sorted()               | double 요소를 오름차순으로 정렬        |
| IntStream    | sorted()               | int 요소를 오름차순으로 정렬           |
| LongStream   | sorted()               | long 요소를 오름차순으로 정렬          |

> sorted(); <br/>
> sorted( (a,b) -> a.compareTo(b) ); <br/>
> sorted( Comparator.naturalOrder() ); <br/>
> sorted( (a,b) -> b.compareTo(a) ); <br/>
> sorted( Comparator.reverseOrder() ); <br/>
> sorted( (a,b) -> { ... } ); <br/>
