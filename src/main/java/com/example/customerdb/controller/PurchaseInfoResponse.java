package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
public class PurchaseInfoResponse {

        //    priceのみをレスポンスとして返却するクラス
        private int price;

        public PurchaseInfoResponse(PurchaseInfo price) {
            this.price = price.getPrice();
        }

        public int getPrice() {
            return price;
        }
    }

