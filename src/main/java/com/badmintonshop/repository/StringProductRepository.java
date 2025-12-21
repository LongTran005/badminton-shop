package com.badmintonshop.repository;

import com.badmintonshop.entity.StringProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StringProductRepository extends JpaRepository<StringProduct, Long> {

    @Query("SELECT s FROM StringProduct s WHERE s.isActive = true")
    List<StringProduct> findAllActive();
}
