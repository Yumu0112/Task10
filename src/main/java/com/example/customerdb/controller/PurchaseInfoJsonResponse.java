package com.example.customerdb.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseInfoJsonResponse {
    private int id;
    private String name;
    private String email;
    private LocalDateTime purchaseDate;
    private int price;

}
