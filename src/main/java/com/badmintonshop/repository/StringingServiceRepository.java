package com.badmintonshop.repository;

import com.badmintonshop.entity.StringingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StringingServiceRepository extends JpaRepository<StringingService, Long> {

    List<StringingService> findByIsActiveTrue();
}
