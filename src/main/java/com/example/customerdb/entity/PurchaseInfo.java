package com.example.customerdb.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
public class PurchaseInfo {
    private int id;
    @NotBlank
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseInfo that = (PurchaseInfo) o;
        return id == that.id && price == that.price && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(purchaseDate, that.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, purchaseDate, price);
    }
}
