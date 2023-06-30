package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;

    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }
    @GetMapping("/purchase-info")
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

    @PostMapping("/purchase-info")
    public ResponseEntity<Map<String,String>> addInfo(@RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.addInfo(purchaseInfo);
        return ResponseEntity.ok(Map.of("status", "character successfully created"));
    }


    @PutMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String,String>> updateInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.updateInfo(id, purchaseInfo);
        return ResponseEntity.ok(Map.of("status", "character successfully updated"));

    }

    @PatchMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String,String>> editInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo)  {
        purchaseInfoService.editInfo(id, purchaseInfo);
        return ResponseEntity.ok(Map.of("status", "Info successfully updated"));
    }

    @DeleteMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, String>> deleteInfo(@PathVariable int id) {
        purchaseInfoService.deleteInfo(id);
        return ResponseEntity.ok(Map.of("status", "Info successfully deleted"));
    }

}
