# 중첩 클래스와 중첩 인터페이스

* 특정 클래스가 여러 클래스와 관계를 이루면 독립된 클래스가 좋고, 한 클래스에만 관계를 가진다면 클래스 내부에 선언하는게 좋다.
* 내부 클래스를 숨김으로써 외부에 노출시키지 않고 코드의 복잡성을 줄일 수 있다.
* 내부 클래스를 선언하듯 인터페이스도 클래스 내부에 선언하여 사용할 수 있다.
    * 이러한 방식은 안드로이드와 같은 UI 프로그래밍에서 이벤트 처리를 목적으로 하는 경우가 많다.
    * 중첩 클래스와 인터페이스를 클래스의 멤버로서 선언하면 멤버 클래스, 메소드 내부에서 선언하면 로컬 클래스라는 이름을 가지고 있다.

## 인스턴스 멤버 클래스

* static 키워드 없이 선언된 클래스를 인스턴스 멤버 클래스라 한다.
* 인스턴스 필드와 메소드만 선언할 수 있으며, 정적 필드와 메소드는 선언이 불가능하다.

## 정적 멤버 클래스

* static 키워드로 선언된 클래스를 말한다.
* 모든 종류의 필드, 메소드를 선언할 수 있다.

## 로컬 클래스

* 메소드 내부에 클래스를 선언할 클래스를 말한다.
* 로컬 클래스의 경우 접근 제한자와 static 을 붙일 수 없다.
* 주로 비동기 처리를 위한 스레드 객체를 만들때 사용한다.

## 중첩 클래스의 접근 제한

* 중첩 클래스를 통해 초기화 하는 경우 static 에 초기화를 할 수 있느냐 없느냐는 결국 지금까지 봐왔던 방식과 같다.
    * 정적 필드에 초기화 하기 위해서는 정적 내부 클래스만 가능하다.
    * 이는, static 키워드가 붙을경우 자바는 메모리 할당을 바로 해주기 때문에 일반 클래스 등은 실행과 동시에 초기화가 되지 않기에 static 에 사용할 수 없다.
* 반대로 인스턴스 멤버 내부 클래스는 클래스의 인스턴스 필드와 메소드/정적 필드와 메소드에 접근이 가능하지만, 내부 정적 클래스는 정적 필드와 메소드에만 접근이 가능하다.
* 단, 로컬 클래스 내부에는 그 메소드의 매개변수와 내부 필드의 값이 final 이 붙은 상태로 복사되어서 로컬 클래스 안에서 사용이 된다.
    * 자바 7 이전까지는 로컬 클래스의 모든 변수를 꼭 final 로 선언을 했어야 한다.
    * 자바 8 이후부터는 final 이 아니어도 선언이 가능하나, 이때에는 필드로 복사되며 final 키워드가 있을 경우 로컬 클래스의 메소드 안에 값이 복사되어 사용되어진다.
    * 자바 8 이후에서 로컬 클래스에 final 이 붙지 않더라도 final 성질은 똑같이 붙는다.

## 중첩 클래스에서 바깥 클래스 참조 얻기

* 중첩 클래스 내부에서 this 를 사용하면, 그 this 를 사용한 내부 클래스의 객체 참조이다.
* 외부 클래스에 접근할 때에는, 그 (외부 클래스 이름).this.field or (외부 클래스 이름).this.method 형태로 할 수 있다.

```JAVA
class A {
    int aField1;
    static int aField2;

    void aMethod1;
    static void aMethod2;

    static class B {
        B() {...}
        int field1;
        static int field2;
        void method1() { print("hello"); };
        static void method2() { print("world"); }

        // aField2, aMethod2 에만 접근이 가능하다.
    }

    class C {
        C() {...}
        int field1;
        void method1() { print("hello"); };
        // static int field2;
        // static void method2() { print("world"); }

        // aField1, aField2, aMethod1, aMethod2 모두 접근이 가능하다.
    }

    void aMethod3(int field1, final int field2) {
        int field3;
        final int field4;
        class D() {
            // int field1;
            // int field3; final 이 없을 경우 로컬 클래스의 필드로 복사되어진다.
            
            D() {...}
            int field5;
            void method1() {
                int field2;
                int field4;
                // final 키워드가 존재 할 경우, 로컬 클래스 내부의 메소드 들의 필드로 복사되어 진다.

                print("hello");
            };
            // static int field2;
            // static void method2() { print("world"); }
            // private int field3;
            // protected void method3() { print("bye~"); }

            // aField1, aField2, aMethod1, aMethod2 모두 접근이 가능하다.
        }

        D d = new D();
        ...
    }
}

public static void main(String[] args) {
    A.C c = new A.C();
    // 정적 내부 클래스는 외부 클래스를 생성하지 않아도 생성이 가능하다.
    static {
        
    }

    A a = new A();
    A.C c = a.new C();
    // 내부 클래스를 선언 및 초기화 하는 방법.
    ...
}
```

## 중첩 인터페이스

* 클래스의 멤버로 선언된 인터페이스를 말하며, 내부에 인터페이스를 구현하는 이유는 해당 클래스와의 긴밀한 관계를 맺는 구현 클래스를 만들기 위해서이다. (특히, UI 프로그래밍에서 이벤트 처리를 목적으로 많이 활용된다.)
    * 예를들어 Button 의 클릭 이벤트를 처리하기 위한 중첩 인터페이스를 구현.
    * 외부에서 그 버튼을 사용하고, 그 버튼의 클릭 이벤트를 처리하기 위해서는 내부에 구현 된 클릭 이벤트를 구현하여 사용한다.

## 익명 객체

* 이름 없는 객체를 말하며, 익명 객체는 단독으로 사용될 수 없고 클래스를 상속하거나 인터페이스를 구현해야만 생성할 수 있다.
* 익명 객체는 필드의 초기값이나 로컬 변수의 초기값, 매개 변수의 매개값으로 주로 대입되며, UI 이벤트 처리 객체나 스레드 객체를 생성할 목적으로 많이 사용된다.
* 구현 클래스나 객체를 다시 재사용 하지 않을 경우 사용할 때 좋다.
* 익명 자식 객체에 새롭게 정의된 필드와 메소드는 익명 자식 객체 내부에서만 사용되므로, 외부에서는 익명 자식 객체의 필드와 메소드를 사용할 수 없다.

### 로컬 클래스와 익명 클래스와의 차이점은 클래스 이름의 존재 여부일 뿐이며 동작 방식은 동일하다.

```JAVA
public class Button() {
    OnClickListener listener;

    void setOnClickListener(OnClickListener listener) {
        this.listener =
    }

    void touch() {
        listenr.onClick();
    }

    interface OnClickListener {
        void onClick();
    }
}

public class showButton implements Button.OnclickListner {
    @Override
    public void onClick() {
        print("클릭 됨");
    }
}

public static void main(String[] args) {
    Button btn1 = new Button();
    Button btn2 = new Button();

    btn1.setOnClickListener(new showButton());
    btn1.touch(); // 클릭 됨

    btn2 = new Button() {
        @Override
        public void onClick() {
            print("익명 자식 구현체 클릭 됨");
        }
    }
    btn2.touch(); // 익명 자식 구현체 클릭 됨

    Button btn3 = new Button.OnClickListener() {
        @Override
        public void onClick() {
            print("익명 구현 객체 클릭 됨");
        }
    }
    btn3.touch(); // 익명 구현 객체 클릭 됨
}
```