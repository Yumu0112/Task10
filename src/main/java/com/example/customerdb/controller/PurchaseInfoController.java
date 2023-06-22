package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@RestController
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;

    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }
    @GetMapping("/")
    public List<PurchaseInfo> info() {
        return purchaseInfoService.findAll();
    }

//    Serviceを使ったpriceのみの一覧表示
    @GetMapping("/pricelist")
    public List<PurchaseInfoResponse> price() {
        List<PurchaseInfo> purchaseInfos = purchaseInfoService.findAll();
        List<PurchaseInfoResponse> purchaseInfoResponses = purchaseInfos.stream().map(PurchaseInfoResponse::new).toList();

        return purchaseInfoResponses;
    }

    @PostMapping("/register")
    public void addInfo(@RequestBody PurchaseInfo purchaseInfo) {
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        purchaseInfo.setPurchaseDate(timestamp);

        purchaseInfoService.addInfo(purchaseInfo);
    }

//    こちらは作成途中です
//    @PutMapping("/{id}")
//    public void editInfo(@RequestBody PurchaseInfo purchaseInfo) {
//        purchaseInfoService.editInfo(purchaseInfo);
//    }

}
