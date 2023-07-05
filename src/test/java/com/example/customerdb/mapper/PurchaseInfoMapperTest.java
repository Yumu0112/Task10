package com.example.customerdb.mapper;

import com.example.customerdb.entity.PurchaseInfo;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PurchaseInfoMapperTest {

    @Autowired
    PurchaseInfoMapper purchaseInfoMapper;

    @Test
    @DataSet(value = "datasets/userList.yml")
    @Transactional
    public void 全てのユーザーが取得できること() {
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.findAll();

        assertThat(purchaseInfoList)
                .hasSize(3)
                .contains(
                        new PurchaseInfo(1, "Tanaka", "aaa@example.com", null, 8080),
                        new PurchaseInfo(2, "Yamada", "bbb@example.com", null, 5400),
                        new PurchaseInfo(3, "Uchida", "ccc@example.com", null, 12000)
                );
    }
}


