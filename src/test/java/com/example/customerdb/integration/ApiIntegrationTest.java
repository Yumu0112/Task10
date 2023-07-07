package com.example.customerdb.integration;

import com.example.customerdb.entity.PurchaseInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
public class ApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/selectWithDateList.yml")
    @Transactional
    void 情報の取得APIを実行すると全ての情報が取得できステータスコードが200であること() throws Exception {
        String response = mockMvc.perform(get("/purchase-info"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""                      
               [
                    {
                       "id":1,
                       "name":"Tanaka",
                       "email":"aaa@example.com",
                       "purchaseDate":"2023-01-15T10:30:00",
                       "price":8080
                    },
                    {
                       "id":2,
                       "name":"Yamada",
                       "email":"bbb@example.com",
                       "purchaseDate":"2023-02-20T15:45:00",
                       "price":5400
                    },
                    {
                       "id":3,
                       "name":"Uchida",
                       "email":"ccc@example.com",
                       "purchaseDate":"2023-03-10T08:00:00",
                       "price":12000
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
                      {
                         "price":8080
                      },
                      {
                         "price":5400
                      },
                      {
                         "price":12000
                      }
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
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/insertInfoList.yml", ignoreCols = {"id", "purchaseDate"})
    void 情報の登録APIを実行したときに正しく情報を登録しステータスコードが200であること() throws Exception {
        PurchaseInfo newPurchaseInfo = new PurchaseInfo(0, "kato", "kto@example.com", null, 9000);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(newPurchaseInfo);

            String response = mockMvc.perform(post("/purchase-info")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
                {
                    "message": "Info successfully created"
                }
                        """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/updatedList.yml", ignoreCols = "id")
    void PUTで指定したidの情報が更新できステータスコードが200であること() throws Exception {
        PurchaseInfo updateInfo = new PurchaseInfo(1, "kinoshita", "knst@example.com", null, 2500);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateInfo);


        String response = mockMvc.perform(put("/purchase-info/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {                 
                   "id": 1,
                   "name": "kinoshita",
                   "email": "knst@example.com",
                   "price": 2500
                }
                """, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void PUTで指定したidが存在しないときは更新せず404エラーを返すこと() throws Exception {
        PurchaseInfo updateInfo = new PurchaseInfo(0, "kinoshita", "knst@example.com", null, 2500);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateInfo);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                2023, 7, 05, 12, 00, 00, 000000,
                ZoneId.of("Asia/Tokyo"));
        try (MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)) {
            zonedDateTimeMockedStatic.when(() -> ZonedDateTime.now()).thenReturn(zonedDateTime);

            String response = mockMvc.perform(put("/purchase-info/7")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
                    {
                        "timestamp":"2023-07-05T12:00+09:00[Asia/Tokyo]",
                        "error":"Not Found",
                        "path":"/purchase-info/7",
                        "status":"404",
                        "message":"ID:7 was not found"
                    }
                    """, response, true);
        }
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/patchUpdateInfoList.yml", ignoreCols = "id")
    void PATCHで指定したidの情報が更新できステータスコードが200であること() throws Exception {
        PurchaseInfo updateInfo = new PurchaseInfo(1, "Tanaka", "aaa@example.com", null, 2000);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateInfo);


        String response = mockMvc.perform(patch("/purchase-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
               {
                       "id":1,
                       "name":"Tanaka",
                       "email":"aaa@example.com",
                       "price":2000                  
               }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/allNullUpdate.yml", ignoreCols = "id")
    void nullが入力された場合でも更新できステータスコードが200で返されること() throws Exception {
        Integer nullableInt = null;
        int intValue = nullableInt != null ? nullableInt.intValue() : 0;
        PurchaseInfo updateInfo = new PurchaseInfo(1, "Tanaka", null, null, intValue);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateInfo);


        String response = mockMvc.perform(patch("/purchase-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
               {

                       "id": 1 ,
                       "name":"Tanaka",
                       "price": 0
               }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    public void nameがnullで入力された際はエラーコード400を返すこと() throws Exception {
        String requestBody = """
                {
                    "name": null,
                    "email": "aaa@example.com",
                    "purchaseDate": null,
                    "price": 8080
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/purchase-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        }


    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void PATCHで指定したidが存在しないときは更新せず404エラーを返すこと() throws Exception {
        PurchaseInfo updateInfo = new PurchaseInfo(7, "Tanaka", "aaa@example.com", null, 2000);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(updateInfo);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                2023, 7, 05, 12, 00, 00, 000000,
                ZoneId.of("Asia/Tokyo"));
        try (MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)) {
            zonedDateTimeMockedStatic.when(() -> ZonedDateTime.now()).thenReturn(zonedDateTime);

            String response = mockMvc.perform(patch("/purchase-info/7")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
                    {
                       "timestamp":"2023-07-05T12:00+09:00[Asia/Tokyo]",
                       "error":"Not Found",
                       "path":"/purchase-info/7",
                       "status":"404",
                       "message":"ID:7 was not found"
                    }
                    """, response, true);
        }
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/deletedList.yml")
    void 指定したidの情報が削除できステータスコードが200であること() throws Exception {
        String response = mockMvc.perform(delete("/purchase-info/3"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""  
                  {
                    "message": "Info successfully deleted"
                  }                  
                """, response, JSONCompareMode.STRICT);
        }


    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void 指定したidが存在しないときは削除せず404エラーを返すこと() throws Exception {
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
                       "timestamp":"2023-07-05T12:00+09:00[Asia/Tokyo]",
                       "error":"Not Found",
                       "path":"/purchase-info/7",
                       "status":"404",
                       "message":"ID:7 was not found"
                    }
                    """, response, true);
        }
    }

}
