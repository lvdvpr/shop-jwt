package com.apple.shop.sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SalesDto {
    private String itemName;
    private Integer price;
    private String username;
    private Integer count;
    private LocalDateTime created;

    public Integer getTotalPrice() {
        return price * count;
    }
}
