package com.apple.shop.consumer;

public class Consumer {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }
    public Integer getAge() {
        return age;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(Integer age) {
        if (age >= 0 && age < 100) {
            this.age = age;
        }
    }
    public void plusAge() {
        if ((this.age + 1) < 100) {
            this.age = this.age + 1;
        }
    }

}
