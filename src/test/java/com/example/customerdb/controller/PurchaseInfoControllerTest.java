package com.example.customerdb.controller;
//
//import com.example.customerdb.entity.PurchaseInfo;
//import com.example.customerdb.service.PurchaseInfoService;
//import com.example.customerdb.service.PurchaseInfoServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.lang.reflect.Array.get;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(PurchaseInfoController.class)
//@ExtendWith(MockitoExtension.class)
public class PurchaseInfoControllerTest {
//    @InjectMocks
//    private PurchaseInfoController purchaseInfoController;
//
//
//    @MockBean
//    private PurchaseInfoServiceImpl purchaseInfoServiceImpl;
//    @Mock
//    private PurchaseInfoService purchaseInfoService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    public void 全てのデータを取得できること() {
//        List<PurchaseInfo> infos = new ArrayList<>();
//        infos.add(new PurchaseInfo(1, "mai", "aaa@example.com", LocalDateTime.parse("2000-01-01T07:09:23"), 8000));
//        infos.add(new PurchaseInfo(2, "takashi", "bbb@example.com", LocalDateTime.parse("2010-01-016T07:09:23"), 8000));
//        infos.add(new PurchaseInfo(3, "taro", "ccc@example.com", LocalDateTime.parse("2005-05-01T07:09:23"), 8000));
//
//        doReturn(infos).when(purchaseInfoService).findAll();
//        mockMvc.perform(get().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(MockMvcResultMatchers.content().json("""
//                        [
//                              {
//                                "id": 1,
//                                "name": "mai",
//                                "email": "aaa@example.com",
//                                "purchaseDate": "2000-01-01T07:09:23",
//                                "price": 8000
//                              },
//                              {
//                                "id": 2,
//                                "name": "takashi",
//                                "email": "bbb@example.com",
//                                "purchaseDate": "2010-01-016T07:09:23",
//                                "price": 4500
//                              },
//                              {
//                                "id": 3,
//                                "name": "taro",
//                                "email": "ccc@example.com",
//                                "purchaseDate": "2005-05-01T07:09:23",
//                                "price": 1980
//                              }
//                        ]
//                        """));
//    }


//    @Test
//    public void testGetPurchaseInfoPriceList() {
//        // Mock the behavior of the service
//        List<PurchaseInfo> mockPurchaseInfos = new ArrayList<>();
//        mockPurchaseInfos.add(new PurchaseInfo());
//        when(purchaseInfoService.findAll()).thenReturn(mockPurchaseInfos);
//
//        // Call the controller method
//        List<PurchaseInfoResponse> result = purchaseInfoController.price();
//
//        // Verify the result
//        assertEquals(mockPurchaseInfos.size(), result.size());
//        verify(purchaseInfoService, times(1)).findAll();
//    }
//
//    @Test
//    public void testAddPurchaseInfo() {
//        // Create a mock PurchaseInfo
//        PurchaseInfo mockPurchaseInfo = new PurchaseInfo();
//
//        // Call the controller method
//        purchaseInfoController.addInfo(mockPurchaseInfo);
//
//        // Verify the service method was called
//        verify(purchaseInfoService, times(1)).addInfo(mockPurchaseInfo);
//    }
//
//    @Test
//    public void testUpdatePurchaseInfo() {
//        // Create a mock PurchaseInfo
//        PurchaseInfo mockPurchaseInfo = new PurchaseInfo();
//        int id = 1;
//
//        // Call the controller method
//        purchaseInfoController.updateInfo(id, mockPurchaseInfo);
//
//        // Verify the service method was called with the correct arguments
//        verify(purchaseInfoService, times(1)).updateInfo(id, mockPurchaseInfo);
//    }
//
//    @Test
//    public void testEditPurchaseInfo() {
//        // Create a mock PurchaseInfo
//        PurchaseInfo mockPurchaseInfo = new PurchaseInfo();
//        int id = 1;
//
//        // Call the controller method
//        purchaseInfoController.editInfo(id, mockPurchaseInfo);
//
//        // Verify the service method was called with the correct arguments
//        verify(purchaseInfoService, times(1)).editInfo(id, mockPurchaseInfo);
//    }
//
//    @Test
//    public void testDeletePurchaseInfo() {
//        int id = 1;
//
//        // Call the controller method
//        ResponseEntity<String> response = purchaseInfoController.deleteInfo(id);
//
//        // Verify the service method was called and the response is correct
//        verify(purchaseInfoService, times(1)).deleteInfo(id);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Purchase info with ID " + id + " has been deleted", response.getBody());
//    }
}
