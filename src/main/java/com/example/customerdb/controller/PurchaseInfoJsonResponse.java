package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseInfoJsonResponse {
    private int id;
    private PurchaseInfo purchaseInfo;

}
