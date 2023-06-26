package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseInfoService {

    List<PurchaseInfo> findAll();
    void addInfo(PurchaseInfo purchaseInfo);
    void updateInfo(int id, PurchaseInfo purchaseInfo);

//    PurchaseInfo findById(int id);
    void deleteInfo(int id);

}
