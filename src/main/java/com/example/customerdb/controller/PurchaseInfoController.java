package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;

    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }

    @GetMapping("/purchase-info")
    public List<PurchaseInfo> getInfo() {
        return purchaseInfoService.findAll();
    }

    @GetMapping("/pricelist")
    public List<PurchaseInfoResponse> getPriceList() {
        List<PurchaseInfo> purchaseInfos = purchaseInfoService.findAll();
        List<PurchaseInfoResponse> purchaseInfoResponses = purchaseInfos.stream().map(PurchaseInfoResponse::new).toList();
        return purchaseInfoResponses;
    }

    @PostMapping("/purchase-info")
    public ResponseEntity<Map<String, String>> addInfo(@Validated @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.addInfo(purchaseInfo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Info successfully created");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/purchase-info/{id}")
    public ResponseEntity<PurchaseInfoJsonResponse> updateInfo(@PathVariable int id, @Validated @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.updateInfo(id, purchaseInfo);
        PurchaseInfoJsonResponse response = new PurchaseInfoJsonResponse(id, purchaseInfo.getName(), purchaseInfo.getEmail(), purchaseInfo.getPurchaseDate(), purchaseInfo.getPrice());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/purchase-info/{id}")
    public ResponseEntity<PurchaseInfoJsonResponse> editInfo(@PathVariable int id, @Validated @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.editInfo(id, purchaseInfo);
        PurchaseInfoJsonResponse response = new PurchaseInfoJsonResponse(purchaseInfo.getId(), purchaseInfo.getName(), purchaseInfo.getEmail(), purchaseInfo.getPurchaseDate(), purchaseInfo.getPrice());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, String>> deleteInfo(@PathVariable int id) {
        purchaseInfoService.deleteInfo(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Info successfully deleted");
        return ResponseEntity.ok(response);
    }
}
