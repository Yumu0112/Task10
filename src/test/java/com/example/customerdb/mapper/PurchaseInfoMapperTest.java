package com.example.customerdb.mapper;

import com.example.customerdb.entity.PurchaseInfo;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PurchaseInfoMapperTest {

    @Autowired
    PurchaseInfoMapper purchaseInfoMapper;

    @Test
    @DataSet(value = "datasets/infoList.yml")
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

    @Test
    @DataSet(value = "datasets/blankList.yml")
    @Transactional
    public void データが存在しない時は空のListを返すこと() {
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.findAll();
        assertThat(purchaseInfoList).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void 指定したidの情報が取得できること() {
        Optional<PurchaseInfo> purchaseInfo = purchaseInfoMapper.findOptionalById(1);
        assertThat(purchaseInfo).contains(
                new PurchaseInfo(1, "Tanaka", "aaa@example.com", null, 8080)
        );
    }

    @Test
    @DataSet(value = "datasets/infoList.yml")
    @Transactional
    public void 指定したidの情報が存在しない時に空の要素を返すこと() {
        Optional<PurchaseInfo> movie = purchaseInfoMapper.findOptionalById(-10);
        assertThat(movie).isEmpty();
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/insertInfoList.yml")
    public void 新しい情報が追加できること() {
        PurchaseInfo newPurchaseInfo = new PurchaseInfo(4, "kato", "kto@example.com", null, 9000);
        purchaseInfoMapper.insert(newPurchaseInfo);
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/updatedList.yml")
    public void 情報を上書きできること() {
        PurchaseInfo updateInfo = new PurchaseInfo(1, "kinoshita", "knst@example.com", null, 2500);
        purchaseInfoMapper.update(1, updateInfo);
    }

    @Test
    @Transactional
    @DataSet(value = "datasets/infoList.yml")
    @ExpectedDataSet(value = "datasets/deletedList.yml")
    public void 情報を削除できること() {
        purchaseInfoMapper.delete(3);
    }

}



