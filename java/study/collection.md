# collection (컬렉션)

### Array (배열)

* 여러 변수를 저장할 수 있는 모음(?) 의 역할을 하는 배열이 존재한다.
  * 자바에서 배열은 저장할 수 있는 객체의 수를 생성할 때 정해야 한다.
  * 넉넉하게 많은 공간을 초기에 만들어서 생성 할 수있지만, 초기화를 하기 때문에 그만큼 메모리 측면에서도 비효율 적이다.
  * 또한, 중간에 있었던 값을 제거하면 중간중간 빈 값을 가지는 배열이 되어서 사용함에 불편함이 많이 존재한다.

## Collection Framework

* 자료구조를 바탕으로 객체들을 효율적으로 추가, 삭제, 검색할 수 있도록 java.util 패키지에 컬렉션과 관련된 인터페이스와 클래스가 존재한다.
  * 컬렉션 - 요소를 수집해서 저장하는 것
  * 프레임워크 - 사용 방법을 미리 정해 놓은 라이브러리
* 컬렉션 객체는 자바 4 이전에 모든 객체를 저장할 수 있지만, 객체를 사용할 때 마다 원래 타입으로 일일이 변환시켜줘야 해서 실행 성능에 좋지 못한 영향을 미쳤었다.
  * 자바 5 에 제네릭을 도입하면서 모든 컬렉션 객체에 타입을 지정해서 사용할 수 있게 변경되었다.
* 컬렉션 프레임워크의 주요 인터페이스
  1. List
     * ArrayList
     * Vector
     * LinkedList
  2. Set
     * HashSet
     * TreeSet
  3. Map
     * HashMap
     * Hashtable
     * TreeMap
     * Properties
* List 와 Set 은 객체 추가, 삭제, 검색하는 방법에 많은 공톰점의 메소드를 모은 Collection 인터페이스가 존재한다.
* Map 은 Key, Value 를 한쌍으로 묶어서 관리하는 구조로 되어 있다.

| 인터페이스 분류          | 특징                                  | 구현 클래스                                  |
| ----------------- | ----------------------------------- | --------------------------------------- |
| Collection - List | - 순서를 유지하고 저장 <br/> - 중복 저장 가능      | ArrayList, Vector, LinkedList           |
| Collection - Set  | - 순서를 유지하지 않고 저장 <br/> - 중복 저장 안 됨  | HashSet, TreeSet                        |
| Map               | - 키와 값의 쌍으로 저장 <br/> - 키는 중복 저장 안 됨 | HashMap, Hashtable, TreeMap, Properties |

## List Colletion

* 객체를 인덱스로 관리하는 구조를 가지고 있다.
* 객체 자체를 저장하는 것이 아닌, 객체의 주소를 저장하여 접근할 때 주소로 객체를 접근한다.
* 리스트 컬렉션은 힙 영역에 존재한다.
* 리스트 컬렉션의 특징
  1. 객체를 저장하면, 자동으로 인덱스가 붙는다.
  2. 인덱스를 통해 객체를 검색, 삭제 할 수 있다.
  3. null 도 저장이 가능하며, 해당 인덱스는 객체를 참조하지 않는 형태가 된다.
  4. List 인터페이스는 제네릭 타입이며, 구현 객체를 생성할 때 구체적인 타입이 정해진다.
* 리스트 컬렉션의 공통적으로 사용 가능한 List 인터페이스

| 기능    | 메소드                        | 설명                          |
| ----- | -------------------------- | --------------------------- |
| 객체 추가 | boolean add(E e)           | 주어진 객체를 맨 끝에 추가             |
|       | void add(int index, E e)   | 주어진 인덱스에 객체를 추가             |
|       | E set(int index, E e)      | 주어진 인덱스에 저장된 객체를 주어진 객체로 변경 |
| 객체 검색 | boolean contains(Object o) | 주어진 객체가 저장되어 있는지 여부 판단      |
|       | E get(int index)           | 주어진 인덱스에 저장된 객체를 리턴         |
|       | boolean isEmpty()          | 컬렉션이 비어 있는지 여부 판단           |
|       | int size()                 | 저장되어 있는 전체 객체 수를 리턴         |
| 객체 삭제 | void clear()               | 저장된 모든 객체를 삭제               |
|       | E remove(int index)        | 주어진 인덱스에 저장된 객체를 삭제         |
|       | boolean remove(Object o)   | 주어진 객체를 삭제                  |

### ArrayList

* List 인터페이스의 구현 클래스이다.
* 배열은 생성할 때 크기가 고정되지만, ArrayLsit 는 자동으로 크기가 증가된다.

> List\<E> list = new ArrayList\<E>();

* 위와 같이 기본 생성자로ArrayList 를 생성하면, 기본적으로 10 개의 저장 공간을 가진 객체를 생성한다.
  * 생성할 때 () 안에 숫자를 넣으면, 초기에 그 숫자 용량을 가지는 ArrayList 를 생성한다.
* ArrayList 에 객체를 추가하면 0 번부터 차례대로 저장되고, 중간 객체가 제거되면 뒤의 객체들이 1개씩 당겨진다.
  * 객체 삽입과 삭제가 빈번이 일어나는 경우에는 ArrayList 의 사용은 바람직하지 않으며, 이럴때는 LinkedList 가 더 좋다.
  * 인덱스 검색이나, 맨 마지막에 객체를 추가하는 경우에는 ArrayList 를 사용하는게 좋다.

> Arrays.asList(T..a); 형태로 고정된 객체로 arrayList 를 만들 수 있다. </br>
> List\<String> list = Arrays.asList("Foo", "Bar", "FooBar");

### Vector

* Vector 는 ArrayList 와 동일한 내부 구조를 가지고 있다.
* 단, Vector 는 동기화된 메소드로 구성되어 있기에 멀티 스레드가 동시에 이 메소드들을 실행할 수 없고, 하나의 스레드가 실행을 완료해야만 다른 스레드를 실행할 수 있다.
  * 즉, 멀티 스레드 환경에서 안전하게 객체를 삭제, 추가가 가능한 Trhead Safe 를 보장한다.

### LinkedList

* LinkedList 는 ArrayList 와 사용방법은 같지만, 내부 구조는 서로 다르다.
* LinkedList 는 인접 참조를 링크해서 체인처럼 관리한다.
  * 즉, 특정 인덱스의 객체를 제거하거나 추가하면, 앞뒤 링크만 변경되고 나머지 링크는 변경되지 않는다.

> List\<E> list = new LinkedList\<E>();

```JAVA
// 추가, 삭제, 검색을 10,000 번 하는 예제

public class Main {
    public static void main(String[] args) {
        ArrayList list1 = new ArrayList<String>();
        LinkedList list2 = new LinkedList<String>();

        System.out.println("ArrayList");
        compareList(list1);

        System.out.println("LinkedList");
        compareList(list2);
    }

    private static void compareList(List list){
        long startTime;
        long endTime;

        System.out.println("==================================================");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.add(randomGenerateString());
        }
        endTime = System.nanoTime();
        System.out.println("순차 추가 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.get(i);
        }
        endTime = System.nanoTime();
        System.out.println("순차 검색 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.remove(0);
        }
        endTime = System.nanoTime();
        System.out.println("순차 삭제 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.add(new Random().nextInt(i + 1), randomGenerateString());
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 추가 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.get(new Random().nextInt(i + 1));
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 검색 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.remove(new Random().nextInt(list.size()));
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 삭제 : " + (endTime - startTime) + " ns");
    }

    private static String randomGenerateString() {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        return new Random().ints(leftLimit, rightLimit + 1)
                .limit(1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
```

![lsit_compare](./images/list_compare.png)

| 구분         | 순차적으로 추가/삭제 | 랜덤 추가/삭제 | 검색  |
| ---------- | ----------- | -------- | --- |
| ArrayList  | 빠르다         | 느리다      | 빠르다 |
| LinkedList | 느리다         | 빠르다      | 느리다 |

* ~~위 표와 같이 된다고는 하는데.. 막상 해보니 표대로 동작하지 않네..~~