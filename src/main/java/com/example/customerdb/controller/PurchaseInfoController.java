package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;

    private final Map<String, Map<String, Object>> responseBody = new HashMap<>();
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
    public PurchaseInfoJsonResponse addInfo(@RequestBody PurchaseInfo purchaseInfo) {
        Map<String, Object> response = new HashMap<>();
        purchaseInfoService.addInfo(purchaseInfo);

        response.put("content", purchaseInfo);

        return new PurchaseInfoJsonResponse(purchaseInfo);
    }


    @PutMapping("/purchase-info/{id}")
    public PurchaseInfoJsonResponse updateInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo) {
        Map<String, Object> response = new HashMap<>();
        purchaseInfoService.updateInfo(id, purchaseInfo);

        response.put("content", purchaseInfo);

        return new PurchaseInfoJsonResponse(purchaseInfo);
    }

    @PatchMapping("/purchase-info/{id}")
    public PurchaseInfoJsonResponse editInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo)  {
        Map<String, Object> response = new HashMap<>();
        purchaseInfoService.editInfo(id, purchaseInfo);

        response.put("content", purchaseInfo);

        return new PurchaseInfoJsonResponse(purchaseInfo);
    }

    @DeleteMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, Object>> deleteInfo(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        purchaseInfoService.deleteInfo(id);

        response.put("message", "Info successfully deleted");

        return ResponseEntity.ok(response);
    }
}
