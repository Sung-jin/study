# Generic (제네릭)

- Java 5 에 추가된 타입.
- 제네릭을 통해 잘못돈 타입이 사용될 수 있는 문제를 해결할 수 있다.
- 제네릭은 클래스와 인터페이스, 메소드를 정의할 떄 타입을 파라미터로 사용할 수 있도록 한다.
- 제네릭의 이점
  1. 컴파일 시 강한 타입 체크를 할 수 있다.
     - 자바 컴파일러는 제네릭 코드에 대한 강한 타입 체크를 한다.
     - 런타임에 타입 에러가 발생하는 경우보다, 컴파일 단계에서 타입 에러를 체크하여 사전에 방지할 수 있다.
  2. Casting 을 제거한다.

```JAVA
List list = new ArrayList();
list.add("Hello world~");
String str = (String) list.get(0);
////////////////////////////////////
List<String> list = new ArrayList<String>();
list.add("Hello world~");
String str = lsit.get(0);
```

## 제네릭 타입

- 타입을 파라미터로 가지는 클래스와 인터페이스를 말한다.
- <GENERIC_TYPE> 이 뒤에 붙는 클래스 또는 인터페이스를 말한다.
- 타입 파라미터는 변수명과 동일한 규칙에 따라 작성할 수 있지만, 일반적으로 대문자 알파벳 한 글자로 표현한다.
- 제네릭 타입을 실제 코드에서 사용하려면 타입 파라미터에 구체적인 타입을 지정해야 한다.

```JAVA
public Class Box {
    private Object object;
    // 최상위 부모 클래스이므로 어떠한 객체가 오던지 저장 할 수 있다.
    public void set(Object object) { this.object = object; }
    public Object get() { return object; }
}

public class Foo {}

...
public static void main(String[] args) {
    Box box = new Box();
    box.set("Bar");
    String str = (String)box.get();;

    box.set(new Foo());
    Foo foo = box.get();
    // 모든 타입을 저장 할 수 있지만, 저장될 때 자동 타입 변환이 일어나고 빼낼 때 강제 타입 변환을 해줘야만 한다.
}

/////////////////////////////////////////////////

public Class Box<T> {
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

public static void main(String[] args) {
    Box box = new Box<String>();
    box.set("Bar");         // 자동 Boxing
    String str = box.get(); // 자동 UnBoxing

    /*
    Box 클래스는 내부적으로 아래와 같이 변한다.
    public Class Box<String> {
        private String t;
        public void set(String t) { this.t = t; }
        public String get() { return t; }
    }
    */
    // 설계할 때는 구체적인 타입을 명시하지 않고, 실제 클래스가 사용될 때 구체적인 타입을 지정함으로써 타입 변환을 최소화 한다.
}
```

## 멀티 타입 파라미터

- 두 개 이상의 타입 파라미터를 사용할 수 있는데, 이 경우 각 타입 파라미터를 콤마로 구분한다.

```JAVA
public class Foo<T, M> {
    private T t;
    private M m;

    public void setT(T t) { this.t = t; }
    public void setT(M m) { this.m = m; }

    public T getT() { return t; }
    public M getM() { return m; }
}

public static void main(String[] args) {
    Foo<String, Integer> foo = new Foo<String, Integer>();
    foo.setT("val1")
    foo.setM(1);

    String str = foo.getT();
    int num = foo.getM();
}
```

## 제네릭 메소드

- 매개 타입과 리턴 타입으로 타입 파라미터를 가지는 메소드를 말한다.
- 제네릭 메소드의 호출 방법
  1. 코드에서 타입 파라미터의 구체적인 타입을 명시적으로 지정
  2. 컴파일러가 매개값의 타입을 보고 구체적인 타입을 추정

```JAVA
public <GENERIC_TYPE> return_type func(GENERIC_TYPE, ...) { ... }

...

return_type val = <detail_type> method(arg...s);
return_type val = method(arg...);

// 제네릭 메소드를 활용한 key,value compare

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) { this.key = key; this.value = value;}

    public void setKey(K key) { this.key = key; }
    public void setValue(V value) { this.value = value; }
    public K getKey() { return key; }
    public K getValue() { return value; }
}

public boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
    boolean keyCompare = p1.getKey().equals(p2.getKey());
    boolean valueCompare = p1.getValue().equals(p2.getValue());
    return keyCompare && valueCompare;
}

...

public static void main(String[] args) {
    Pair<String, Integer> p1 = new Pair<String, Integer>("Foo", 1);
    Pair<String, Integer> p2 = new Pair<String, Integer>("Foo", 1);
    Pair<String, Integer> p3 = new Pair<String, Integer>("Bar", 1);
    Pair<String, Integer> p4 = new Pair<String, Integer>("Foo", 2);
    Pair<String, Integer> p5 = new Pair<String, Integer>("Bar", 2);
    print("동일한가 ? : {}", <String, Integer>compare(p1, p2)); // 동일한가 ? : true
    // 제네릭 메소드에 명시적으로 어떤 타입이 들어올지 코드로 위와 같이 <> 안에 넣을 수 있다.
    print("동일한가 ? : {}", compare(p1, p3)); // 동일한가 ? : false
    // 명시적으로 넣지 않으면, 컴파일러가 매개 타입을 보고 추정
    print("동일한가 ? : {}", compare(p1, p4)); // 동일한가 ? : false
    print("동일한가 ? : {}", compare(p1, p5)); // 동일한가 ? : false
}
```

## Bounded Type Parameter 제한된 타입 파라미터 (<T extends 최상위타입>)

- 타입 파라미터에 구체적인 타입을 제한할 필요가 있을 경우 사용된다.
- 타입 파라미터 뒤에 extends 키워드를 붙이고 상위 타입을 명시하면 제한된 타입 파라미터가 선언된다.
  - extends 키워드가 있지만, implement 키워드를 사용하지는 않는다.

```JAVA
public <T extends 상위타입> return_type method(arg...) { }
```

- 타입 파라미터에 지정되는 구체적인 타입은 상위 타입이거나 상위 타입의 하위 또는 구현 클래스만 가능하다.
- 메소드의 {} 안에서 타입 파라미터 변수로 사용 가능한 것은 상위 타입의 멤버로 제한된다.
  - 하위 타입에만 있는 필드와 메소드는 사용할 수 없다.

```JAVA
// 위에 정의된 compare 을 Number 에 제한한 제네릭 메소드로 변환한 코드이다.

public <T extends Numbers> int numberCompare(T t1, T t2) {
    double v1 = t1.doubleValue();   // Number 의 doubleValue() 메소드 사용
    double v2 = t2.doubleValue();   // doubleValue() 이기 때문에 모든 number 타입에 대응이 된다.
    return Double.compare(v1, v2);
    // 왼쪽이 크면 -1, 같으면 0, 오른쪽이 크면 1
}

public static void main(String[] args) {
    //int result1 = numberCompare("a", "b");
    // a, b 는 Number 타입이 아님

    int result2 = numberCompare(10, 20); // 1
    int result3 = numberCompare(20.0, 10.0); // -1
    int result4 = numberCompare(10, 10); // 0
}
```

## 와일드카드 타입 (<?>, <? extends ...>, <? super ...>)

- ? 를 wildcard 라고 한다.
- 제네릭 타입을 매개값이나 리턴 타입으로 사용할 때 구체적인 타입 대신에 와일드 카드로 다음과 같은 형태로 사용할 수 있다.
  1. 제네릭타입<?> : Unbounded Wildcards (제한없음)
     - 타입 파라미터를 대치하는 구체적인 타입으로 모든 클래스나 인터페이스 타입이 올 수 있다.
  2. 제네릭타입<? extends 상위타입> : Upper Bounded Wildcards (상위 클래스 제한)
     - 타입 파라미터를 대치하는 구체적인 타입으로 상위 타입이나 하위 타입만 올 수 있다.
  3. 제네릭타입<? super 하위타입> : Lower Bounded Wildcards (하위 클래스 제한)
     - 타입 파라미터를 대치하는 구체적인 타입으로 하위 타입이나 상위 타입이 올 수 있다.

```JAVA
public class Person {}
public class Worker extends Person {}
public class Student extends Person {}
public class highStudent extends Student {}

...

public class Course<T> {
    private String name;
    private T[] students;

    public Course(String name, int capacity) {
        this.name = name;
        students = (T[]) (new Object[capacity]);
        // 타입 파라미터로 배열을 생성하려면 new T[n] 형태로 배열을 생성할 수 없고 (T[]) (new Object[n]) 으로 생성해야 한다.
    }

    public String getName() { return name; }
    public T[] getStudents() { return students; }
    public void add(T t) {
        for(int i = 0; i < students.length; i++) {
            if(students[i] == null) {
                students[i] = t;
                break;
            }
        }
    }
}

// Course<?> : 모든 타입 (Person, Worker, Studnet, HighStudenr) 가 가능하다.
// Course<? extends Student> : Student 와 HighStuduent 만 가능하다.
// Course<? super Worker> : Worker 와 Person 만 가능하다.

public void registerCourse(Course<?> course) {
    print(`${course.getName()} 수강생 ${Arrays.toString(course.getStudents()))}`);
}
// 모든 타입

public void registerCouseStudent(Course<? extends Student> course) {
    print(`${course.getName()} 수강생 ${Arrays.toString(course.getStudents()))}`);
}
// Student 와 HighStudent

public void registerCourseWorker(Course<? super Worker> course) {
    print(`${course.getName()} 수강생 ${Arrays.toString(course.getStudents()))}`);
}
// Worker 와 Person

public static void main(String[] args) {
    Course<Person> personCourse = new Course<Person>("일반인과정", 4);
    personCourse.add(new Person("일반인"));
    personCourse.add(new Worker("직장인"));
    personCourse.add(new Student("학생"));
    personCourse.add(new HighStudent("고등학생"));

    Course<Person> workerCourse = new Course<Worker>("직장인과정", 1);
    workerCourse.add(new Worker("직장인"));

    Course<Student> studentCourse = new Course<Student>("학생과정", 2);
    studentCourse.add(new Student("학생"));
    studentCourse.add(new HighStudent("고등학생"));

    Course<HighStudent> highStudentCourse = new Course<HighStudent>("고등학생과정", 1);
    studentCourse.add(new HighStudent("고등학생"));

    registerCourse(personCourse);
    registerCourse(workerCourse);
    registerCourse(studentCourse);
    registerCourse(highStudentCourse);

    registerCouseStudent(personCourse);         // Course<? extends Student>
    registerCouseStudent(workerCourse);         // 이므로 Student | highStudnet 만 가능
    registerCouseStudent(studentCourse);
    registerCouseStudent(highStudentCourse);

    registerCourseWorker(personCourse);
    registerCourseWorker(workerCourse);
    registerCourseWorker(studentCourse);        // Course<? super Worker>
    registerCourseWorker(highStudentCourse);    // 이므로 Person | Worker 만 가능
}
```

## 제네릭 타입의 상속과 구현

- 제네릭 타입도 부모 클래스가 될 수 있다.
- 자식 제네릭 타입은 추가적으로 타입 파라미터를 가질 수 있다.
- 제네릭 인터페이스를 구현한 클래스도 제네릭 타입이 된다.

```JAVA
public class Foo<T, M> {}
public class Bar<T, M, C> extends Foo<T, M> {}

...

public interface Storage<T> {
    public void add(T item, int index);
    public T get(int index);
}

public class StorageImpl<T> implements Storage<T> {
    private T[] array;

    public StorageImpl(int capacity) {
        this.array = (T[]) (new Object[capacity]);
        // 타입 파라미터로 배열을 생성하라면 new T[n] 형태로 생성할 수 없고 (T[]) (new Object[n]) 으로 생성해야 한다.
    }

    @Override
    public void add(T item, int index) {
        array[index] = item;
    }

    @Override
    public T get(int index) {
        return array[index];
    }
}

...

public static void main(String[] args) {
    Storage<String> storage = new StorageImpl<String>(100);
    storage.add("Foo", 0);
    storage.add("Bar", 1);
    storage.add("Hello", 2);
    storage.add("World", 3);

    for (i in 1..4) { print(storage.get(i)); } // Foo Bar Hello World
}
```
