package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByPhoneNumberAndVisible(String phoneNumber, Boolean visible);

    Optional<ProfileEntity> findByPhoneNumber(String phoneNumber);


    @Modifying
    @Transactional
    @Query(value = "update  profile p set p.status=?2 where p.phoneNumber=?1", nativeQuery = true)
    void updateStatusByPhone(String phone, ProfileStatus status);
}
