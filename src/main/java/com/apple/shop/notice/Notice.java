package com.apple.shop.notice;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    @Temporal(TemporalType.DATE)
    public Date date;
}
