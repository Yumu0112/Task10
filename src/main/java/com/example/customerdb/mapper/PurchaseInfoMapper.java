
package com.example.customerdb.mapper;

import com.example.customerdb.entity.PurchaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PurchaseInfoMapper {
    @Select("SELECT * FROM purchase_info")
    List<PurchaseInfo> findAll();
}