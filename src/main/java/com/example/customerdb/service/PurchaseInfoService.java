package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;

import java.util.List;

public interface PurchaseInfoService {

    List<PurchaseInfo> findAll();
    void addInfo(PurchaseInfo purchaseInfo);
    void updateInfo(int id, PurchaseInfo purchaseInfo);
    void deleteInfo(int id);
}
