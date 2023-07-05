package com.example.customerdb.controller;


import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.service.PurchaseInfoService;
import com.example.customerdb.service.PurchaseInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseInfoController.class)
@ExtendWith(MockitoExtension.class)
public class PurchaseInfoControllerTest {
    @InjectMocks
    private PurchaseInfoController purchaseInfoController;
    @MockBean
    private PurchaseInfoServiceImpl purchaseInfoServiceImpl;
    @Mock
    private PurchaseInfoService purchaseInfoService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 全てのデータを取得できること() throws Exception {
        List<PurchaseInfo> purchaseInfos = new ArrayList<>();
        purchaseInfos.add(new PurchaseInfo(1, "mai", "aaa@example.com", LocalDateTime.parse("2000-01-01T07:09:23"), 8000));
        purchaseInfos.add(new PurchaseInfo(2, "takashi", "bbb@example.com", LocalDateTime.parse("2010-01-01T07:09:23"), 4500));
        purchaseInfos.add(new PurchaseInfo(3, "taro", "ccc@example.com", LocalDateTime.parse("2005-05-01T07:09:23"), 1980));

        doReturn(purchaseInfos).when(purchaseInfoServiceImpl).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/purchase-info").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                              {
                                "id": 1,
                                "name": "mai",
                                "email": "aaa@example.com",
                                "purchaseDate": "2000-01-01T07:09:23",
                                "price": 8000
                              },
                              {
                                "id": 2,
                                "name": "takashi",
                                "email": "bbb@example.com",
                                "purchaseDate": "2010-01-01T07:09:23",
                                "price": 4500
                              },
                              {
                                "id": 3,
                                "name": "taro",
                                "email": "ccc@example.com",
                                "purchaseDate": "2005-05-01T07:09:23",
                                "price": 1980
                              }
                        ]
                        """));
    }


    @Test
    public void 価格のみを返すレスポンスを返すこと() throws Exception {
        List<PurchaseInfo> purchaseInfos = new ArrayList<>();
        purchaseInfos.add(new PurchaseInfo(1, "mai", "aaa@example.com", LocalDateTime.parse("2000-01-01T07:09:23"), 8000));
        purchaseInfos.add(new PurchaseInfo(2, "takashi", "bbb@example.com", LocalDateTime.parse("2010-01-01T07:09:23"), 4500));
        purchaseInfos.add(new PurchaseInfo(3, "taro", "ccc@example.com", LocalDateTime.parse("2005-05-01T07:09:23"), 1980));

        doReturn(purchaseInfos).when(purchaseInfoServiceImpl).findAll();

        List<PurchaseInfoResponse> result = purchaseInfoController.getPriceList();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pricelist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "price": 8000
                            },
                            {
                                "price": 4500
                            },
                            {
                                "price": 1980
                            }
                        ]
                        """));
    }

    @Test
    public void 新規でデータを登録できること() throws Exception {
        PurchaseInfo purchaseInfo = new PurchaseInfo(4, "kai", "ddd@example.com", null, 9999);
        when(purchaseInfoServiceImpl.addInfo(purchaseInfo)).thenReturn(purchaseInfo);

        String requestBody = """
                {
                    "name": "ziro",
                    "email": "ziro@example.com",
                    "price": 1900
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/purchase-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                             "content": {
                                    "id": 0,
                                    "name": "ziro",
                                    "email": "ziro@example.com",
                                    "purchaseDate": null,
                                    "price": 1900
                                }
                        }
                        """));
    }

    @Test
    public void PUTで指定されたidのデータが更新できること() throws Exception {
        PurchaseInfo purchaseInfo = new PurchaseInfo(4, "kai", "ddd@example.com", null, 9999);
        when(purchaseInfoServiceImpl.updateInfo(4, purchaseInfo)).thenReturn(purchaseInfo);

        String requestBody = """
                {
                    "name": "hachiro"
               
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/purchase-info/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                       {
                           "content": {
                               "id": 0,
                               "name": "hachiro",
                               "email": null,
                               "purchaseDate": null,
                               "price": 0
                           }
                       }
                        """));
    }

    @Test
    public void PATCHで指定されたidのデータが更新できること() throws Exception {
        PurchaseInfo purchaseInfo = new PurchaseInfo(4, "kai", "ddd@example.com", null, 9999);
        when(purchaseInfoServiceImpl.updateInfo(4, purchaseInfo)).thenReturn(purchaseInfo);

        String requestBody = """
                {
                    "price": 7878
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/purchase-info/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "content": {
                                     "id": 0,
                                     "name":null,
                                     "email": null,
                                     "purchaseDate": null,
                                     "price": 7878
                            }
                           }
                   
                        """));
    }


    @Test
    public void 指定されたIDのデータが削除できること() throws Exception {
        when(purchaseInfoServiceImpl.deleteInfo(1)).thenReturn(null);

        String response = mockMvc.perform(delete("/purchase-info/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
        {
             "message": "Info successfully deleted"
        }
        """, response, JSONCompareMode.STRICT);
    }

}
