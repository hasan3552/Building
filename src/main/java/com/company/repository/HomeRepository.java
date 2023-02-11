package com.company.repository;

import com.company.entity.HomeEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HomeRepository extends JpaRepository<HomeEntity, String> {

     Optional<HomeEntity> findByIdAndStatus(String id, Boolean visible);

     @Query(value = "update home_entity  set visible = false where id = ?1", nativeQuery = true)
     void deletedHome(String id);

     @Query(value = "select h.id from HomeEntity as h where h.profile_id = ?1")
     String getHomeIdByProfileId(Integer id);

}
