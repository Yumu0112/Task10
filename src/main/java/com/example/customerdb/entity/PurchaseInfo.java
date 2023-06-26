package com.example.customerdb.entity;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class PurchaseInfo {
    private int id;
    private String name;
    private String email;
    private LocalDateTime purchaseDate;
    private int price;

    public PurchaseInfo(int id, String name, String email, LocalDateTime purchaseDate, int price) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }
}
