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
