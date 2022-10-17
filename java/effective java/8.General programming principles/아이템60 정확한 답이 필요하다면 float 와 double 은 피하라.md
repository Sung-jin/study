## 정확한 답이 필요하다면 float 와 double 은 피하라

* float, double 은 과학과 공학 계산용으로 설계되었다
* 이진 부동소수점 연산에 사용되며, 넓은 범위의 수를 빠르게 정밀한 **근사치**로 계산하도록 설계되었다
* 따라서 정확한 결과가 필요한 곳에서는 float/double 타입은 사용하면 안된다

```java
System.out.println(1.03 - 0.42); // 0.6100000000000001
System.out.println(1.00 - 9 * 0.10); // 0.099999999999998
```

### BigDecimal

* 위와 같은 상황에서 올바르게 해결하기 위해서는 BigDecimal 혹은 int/long 등의 타입을 사용해야 한다

```java
BigDecimal TEN_CENTS = new BigDecimal(".10");
int itemsBought = 0;
BigDecimal funds = new BigDecimal("1.00");

for (BigDecimal priece = TEN_CENTS; funds.compareTo(price) >= 0; price = value.add(TEN_CENTS)) {
    funds = funds.substract(price);
    itemsBought++;
}

println(itemsBougth + "개 구입");
println("잔돈: " + funds);
```

* 위와 같이 BigDecimal 을 사용하면 소수점도 정확한 계산이 가능하다
* 하지만 기본 타입보다 사용하기 훨씬 불편하고 훨씬 느리다
* int, long 타입으로 BigDecimal 을 대안할 수 있다
    * 하지만 이는 다룰수 있는 값의 크기가 제한되고 소수점을 직접 관리해야 한다

```java
int itemsBopught = 0;
int funds = 100;
for (int price = 10l funds >= price; price +=10) {
    funds -= price;
    itemsBought++;
}

println(itemsBougth + "개 구입");
println("잔돈(센트): " + funds);
```

## 정리

* 정확한 답이 필요한 계산에서는 float/double 사용은 피해야 한다
* 소수점 추적은 시스템에 맡기고 코딩시에 불편함이나 성능 저하를 신경쓰지 않는다면 BigDecimal 을 사용할 수 있다
* 성능이 중요하고 소수점을 직접 추절할 수 있으며 숫자가 크지 않다면 int/long 을 사용할 수 있다
    * 단, 18 자리가 넘어가면 BigDecimal 을 사용해야 한다
