package com.example.customerdb.integration;

import com.example.customerdb.entity.PurchaseInfo;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    void 情報の取得APIを実行すると全ての情報が取得できステータスコードが200であること() throws Exception {
        String response = mockMvc.perform(get("/purchase-info"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(""" 
                      
                      [
                      {
                          "id": 1,
                              "name": "Tanaka",
                              "email": "aaa@example.com",
                              "purchaseDate": null,
                              "price": 8080
                      },
                      {
                          "id": 2,
                              "name": "Yamada",
                              "email": "bbb@example.com",
                              "purchaseDate": null,
                              "price": 5400
                      },
                      {
                          "id": 3,
                              "name": "Uchida",
                              "email": "ccc@example.com",
                              "purchaseDate": null,
                              "price": 12000
                      }
                ]
                           """, response, JSONCompareMode.STRICT);
    }

  @Test
  @DataSet(value = "datasets/infoList.yml")
  @ExpectedDataSet(value = "datasets/priceList.yml")
  @Transactional
  void 価格情報の取得APIを実行すると全ての価格のみの情報が取得できステータスコードが200であること() throws Exception {
      String response = mockMvc.perform(get("/pricelist"))
              .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
          JSONAssert.assertEquals("""  
    [
       { "price": 8080 },
       { "price": 5400 },
       { "price": 12000 }
    ]                     
    """, response, JSONCompareMode.STRICT);
    }



    @Test
    @DataSet(value = "datasets/blankList.yml")
    @ExpectedDataSet(value = "datasets/blankList.yml")
    void 情報の取得APIを実行したときに情報がなければ空のリストを返却しステータスコードが200であること() throws Exception {
        String response = mockMvc.perform(get("/purchase-info"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""  
                    [
                    
                    ]                   
                """, response, JSONCompareMode.STRICT);
        }

    @Test
    @DataSet(value = "datasets/infoList")
    @ExpectedDataSet(value = "datasets/insertInfoList.yml")
    void 情報の登録APIを実行したときに正しく情報を登録しステータスコードが200であること() throws Exception {
        PurchaseInfo newPurchaseInfo = new PurchaseInfo(4, "kato", "kto@example.com", null, 9000);

        mockMvc.perform(MockMvcRequestBuilders.post("/purchase-info"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void 指定IDが存在しない時削除せず404エラーを返すこと() throws Exception {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                2023, 7, 05, 12, 00, 00, 000000,
                ZoneId.of("Asia/Tokyo"));
        try (MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)) {
            zonedDateTimeMockedStatic.when(() -> ZonedDateTime.now()).thenReturn(zonedDateTime);

            String response = mockMvc.perform(delete("/purchase-info/7"))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
                    {
                       "timestamp": "2023-07-05T12:00+09:00[Asia/Tokyo]",
                       "error": "Not Found",
                       "path": "/purchase-info/7",
                       "status": "404",
                       "message": "ID:0 was not found"
                    }
                    """, response, true);
        }
    }

}