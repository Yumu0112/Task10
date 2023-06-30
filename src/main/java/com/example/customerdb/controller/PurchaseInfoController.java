package com.example.customerdb.controller;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;
    private final HttpStatus httpStatus = HttpStatus.OK;

    private final Map<String, Object> response = new HashMap<>();

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
    public ResponseEntity<Map<String, Map<String, Object>>> addInfo(@RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.addInfo(purchaseInfo);

        response.put("content", purchaseInfo);
        responseBody.put(httpStatus.toString(), response);

        return ResponseEntity.ok(responseBody);
    }


    @PutMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, Map<String, Object>>> updateInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo) {
        purchaseInfoService.updateInfo(id, purchaseInfo);

        response.put("content", purchaseInfo);
        responseBody.put(httpStatus.toString(), response);

        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, Map<String, Object>>> editInfo(@PathVariable int id, @RequestBody PurchaseInfo purchaseInfo)  {
        purchaseInfoService.editInfo(id, purchaseInfo);

        response.put("content", purchaseInfo);
        responseBody.put(httpStatus.toString(), response);

        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/purchase-info/{id}")
    public ResponseEntity<Map<String, Object>> deleteInfo(@PathVariable int id) {
        purchaseInfoService.deleteInfo(id);

        response.put("status", httpStatus.toString());
        response.put("message", "Info successfully deleted");

        return ResponseEntity.ok(response);
    }
}