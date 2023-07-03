package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;

import java.util.List;

public interface PurchaseInfoService {

    List<PurchaseInfo> findAll();
    PurchaseInfo addInfo(PurchaseInfo purchaseInfo);
    PurchaseInfo updateInfo(int id, PurchaseInfo purchaseInfo);

    PurchaseInfo editInfo(int id, PurchaseInfo purchaseInfo);
    PurchaseInfo deleteInfo(int id);
}
