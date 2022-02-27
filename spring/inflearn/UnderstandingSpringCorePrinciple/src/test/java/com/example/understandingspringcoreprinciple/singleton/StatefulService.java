package com.example.understandingspringcoreprinciple.singleton;

public class StatefulService {
    private int price; // 상태를 유지하는 필드
    // 이러한 공유되는 필드가 없어야 한다

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
