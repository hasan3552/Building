package com.company.repository;

import com.company.entity.HomeEntity;
import com.company.entity.PaymentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Integer> {

    List<PaymentTypeEntity> findAllByHome_idAndVisible(String homeId, Boolean visible);
}
