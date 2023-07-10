
package com.example.customerdb.mapper;

import com.example.customerdb.entity.PurchaseInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper
@Component
public interface PurchaseInfoMapper {
    @Select("SELECT * FROM purchase_info")
    List<PurchaseInfo> findAll();

    @Insert("INSERT INTO purchase_info (name, email,purchaseDate, price) VALUES (#{name}, #{email}, #{purchaseDate}, #{price})")
    @Options(useGeneratedKeys=true, keyColumn="id")
    void insert(PurchaseInfo purchaseInfo);

    @Update("UPDATE purchase_info SET name = #{purchaseInfo.name}, email = #{purchaseInfo.email}, purchaseDate = #{purchaseInfo.purchaseDate}, price = #{purchaseInfo.price} WHERE id = #{id}")
    void update(int id, @Param("purchaseInfo") PurchaseInfo purchaseInfo);
    @Select("SELECT * FROM purchase_info WHERE id = #{id}")
    Optional<PurchaseInfo> findOptionalById(int id);
    @Delete("DELETE FROM purchase_info WHERE id = #{id}")
    void delete(int id);
}


