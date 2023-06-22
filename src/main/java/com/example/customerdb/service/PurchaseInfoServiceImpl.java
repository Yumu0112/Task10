package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.mapper.PurchaseInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.customerdb.mapper.PurchaseInfoMapper.*;

@Service
public class PurchaseInfoServiceImpl implements PurchaseInfoService {

    private final PurchaseInfoMapper purchaseInfoMapper;

    public PurchaseInfoServiceImpl(PurchaseInfoMapper purchaseInfoMapper) {
        this.purchaseInfoMapper = purchaseInfoMapper;
    }
    public List<PurchaseInfo> findAll() {
        return purchaseInfoMapper.findAll();
    }

    @Override
    public void addInfo(PurchaseInfo purchaseInfo) {
        purchaseInfoMapper.insert(purchaseInfo);
    }

    @Override
    public void editInfo(PurchaseInfo purchaseInfo) {
        purchaseInfoMapper.update(purchaseInfo);
    }
}