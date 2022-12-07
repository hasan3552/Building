package com.company.repository;

import com.company.entity.HomeAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeAttachRepository extends JpaRepository<HomeAttachEntity, Integer> {

    @Query(value = "select ha.attachId from HomeAttachEntity as ha where ha.homeId = ?1")
    List<String> attachesOfHomeById(String homeId);
}
