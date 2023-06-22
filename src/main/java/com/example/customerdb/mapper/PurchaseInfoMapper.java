
package com.example.customerdb.mapper;

import com.example.customerdb.entity.PurchaseInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PurchaseInfoMapper {
    @Select("SELECT * FROM purchase_info")
    List<PurchaseInfo> findAll();

    @Insert("INSERT INTO purchase_info (name, email,purchaseDate, price) VALUES (#{name}, #{email}, #{purchaseDate}, #{price})")
    void insert(PurchaseInfo purchaseInfo);

    @Update("UPDATE purchase_info SET name = #{name}, email = #{email}, price = #{price}")
    void update(PurchaseInfo purchaseInfo);
}
