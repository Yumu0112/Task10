package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/application")
    public void addInfo(@RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.addInfo(purchaseInfo);
    }

    @PutMapping("/{id}")
    public void updateInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.updateInfo(id, purchaseInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteInfo(@PathVariable int id) {
        purchaseInfoService.deleteInfo(id);
    }

}
