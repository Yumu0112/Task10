package com.example.customerdb.service;

import com.example.customerdb.entity.PurchaseInfo;
import com.example.customerdb.exception.NotFoundException;
import com.example.customerdb.mapper.PurchaseInfoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseInfoServiceImplTest {
    @InjectMocks
    PurchaseInfoServiceImpl purchaseInfoService;

    @Mock
    PurchaseInfoMapper purchaseInfoMapper;

    @Test
    public void 全てのユーザー情報が一覧で取得できること() throws Exception {
        List<PurchaseInfo> purchaseInfos = new ArrayList<>();
        purchaseInfos.add(new PurchaseInfo(1, "mei", "aaa@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 6767));
        purchaseInfos.add(new PurchaseInfo(2, "satsuki", "bbb@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 1111));
        purchaseInfos.add(new PurchaseInfo(3, "tetsuo", "ccc@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 2525));
        purchaseInfos.add(new PurchaseInfo(4, "kai", "ddd@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 9080));
        doReturn(purchaseInfos).when(purchaseInfoMapper).findAll();
        List<PurchaseInfo> actual = purchaseInfoMapper.findAll();
        assertThat(actual).isEqualTo(purchaseInfos);
        verify(purchaseInfoMapper, times(1)).findAll();
    }

    //    1秒以下の誤差は許容範囲とする
    @Test
    public void 現在時刻が正常に設定されること() {
        PurchaseInfo purchaseInfo = new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767);
        purchaseInfoService.addInfo(purchaseInfo);
        LocalDateTime expectedTime = LocalDateTime.now();
        LocalDateTime actualTime = purchaseInfo.getPurchaseDate();
        int toleranceSeconds = 1;
        assertTrue(actualTime.isAfter(expectedTime.minusSeconds(toleranceSeconds))
                && actualTime.isBefore(expectedTime.plusSeconds(toleranceSeconds)));
    }

    @Test
    public void 適切なリクエストボディでデータを追加できること() {
        PurchaseInfo purchaseInfo = new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767);
        PurchaseInfo actual = purchaseInfoService.addInfo(purchaseInfo);
        verify(purchaseInfoMapper, times(1)).insert(any(PurchaseInfo.class)); // insertメソッドが1回呼ばれたことを検証
        assertEquals(purchaseInfo.getName(), actual.getName());
        assertEquals(purchaseInfo.getEmail(), actual.getEmail());
        assertEquals(purchaseInfo.getPurchaseDate(), actual.getPurchaseDate());
        assertEquals(purchaseInfo.getPrice(), actual.getPrice());

    }



    @Test
    public void 指定されたidに該当するレコードがなかったら例外をthrowすること() {
//        テスト用にインスタンス生成
        int id = 0;
        PurchaseInfo purchaseInfo = new PurchaseInfo(0, "mei", "aaa@example.com", null, 6767);

//        idの一致を確認するメソッドの呼び出し
        doReturn(Optional.empty()).when(purchaseInfoMapper).findOptionalById(id);

//        updateInfo()が呼ばれずにfindOptionalById(id)で処理が終了するのを期待
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> purchaseInfoService.updateInfo(id, purchaseInfo))
                .withMessageMatching("ID:" + id + " was not found");

//        だから呼び出しは0回
        verify(purchaseInfoMapper, times(0)).update(id, purchaseInfo);

        verify(purchaseInfoMapper, times(1)).findOptionalById(id);
    }

    @Test
    public void 指定されたidに該当するレコードを更新できること() {
        PurchaseInfo purchaseInfo = new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767);
        PurchaseInfo expectedInfo = new PurchaseInfo(1, "takashi", "bbb@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 9000);
        doReturn(Optional.of(expectedInfo)).when(purchaseInfoMapper).findOptionalById(1);

        PurchaseInfo actualInfo = purchaseInfoService.updateInfo(1, purchaseInfo);

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    void 指定したidのレコードの一部を更新できること() {
        PurchaseInfo purchaseInfo = new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767);
        PurchaseInfo expectedInfo = new PurchaseInfo(1, "takashi", "bbb@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 9000);
        doReturn(Optional.of(expectedInfo)).when(purchaseInfoMapper).findOptionalById(1);

        PurchaseInfo actualInfo = purchaseInfoService.updateInfo(1, purchaseInfo);

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    public void 一部が空欄でも更新作業ができること() {
        doReturn(Optional.of(new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767))).when(purchaseInfoMapper).findOptionalById(1);

        PurchaseInfo info = new PurchaseInfo(1, null, "bbb@example.com", LocalDateTime.parse("2017-09-27T19:23:31"), 8989);
        PurchaseInfo updateInfo = purchaseInfoService.updateInfo(1, info);

        assertThat(updateInfo.getId()).isEqualTo(1);
        assertThat(updateInfo.getName()).isEqualTo(null);
        assertThat(updateInfo.getEmail()).isEqualTo("bbb@example.com");
        assertThat(updateInfo.getPurchaseDate()).isEqualTo("2017-09-27T19:23:31");
        assertThat(updateInfo.getPrice()).isEqualTo(8989);

    }

    @Test
    void 指定したidのレコードが削除できること() {
        PurchaseInfo expectedInfo = new PurchaseInfo(1, "mei", "aaa@example.com", null, 6767);
        doReturn(Optional.of(expectedInfo)).when(purchaseInfoMapper).findOptionalById(1);

        PurchaseInfo actualMovie = purchaseInfoService.deleteInfo(1);

        assertEquals(expectedInfo, actualMovie);
    }


}

