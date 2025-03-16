package com.apple.shop.consumer;

import org.springframework.stereotype.Controller;

@Controller
public class ConsumerController {
    Consumer consumer = new Consumer();

    void setAccount() {
        consumer.setName("홍길동");
        consumer.setAge(33);
        consumer.plusAge();
        System.out.println(consumer.getAge()+consumer.getName());
    }

    public static void main(String[] args) {
        ConsumerController controller = new ConsumerController();
        controller.setAccount();
    }
}
