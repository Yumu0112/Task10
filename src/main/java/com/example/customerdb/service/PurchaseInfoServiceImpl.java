package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.mapper.PurchaseInfoMapper;
import com.example.customerdb.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


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
    public PurchaseInfo addInfo(PurchaseInfo purchaseInfo) {
        LocalDateTime currentTime = LocalDateTime.now();
        purchaseInfo.setPurchaseDate(currentTime);

        purchaseInfoMapper.insert(purchaseInfo);
        return purchaseInfo;
    }

    @Override
    public PurchaseInfo updateInfo(int id, PurchaseInfo purchaseInfo) {
        PurchaseInfo existingInfo = purchaseInfoMapper.findOptionalById(id).orElseThrow(() -> new NotFoundException(id));
        purchaseInfoMapper.update(id, purchaseInfo);
        return purchaseInfo;
    }

    @Override
    public PurchaseInfo editInfo(int id, PurchaseInfo purchaseInfo) {
        PurchaseInfo existingInfo = purchaseInfoMapper.findOptionalById(id).orElseThrow(() -> new NotFoundException(id));
        return existingInfo;
    }


    @Override
    public PurchaseInfo deleteInfo(int id) {
        PurchaseInfo deleted = purchaseInfoMapper.findOptionalById(id).orElseThrow(() -> new NotFoundException(id));
        purchaseInfoMapper.delete(id);
        return deleted;
    }
}
