package com.company.repository;

import com.company.entity.HomeLikeEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public interface HomeLikeRepository extends CrudRepository<HomeLikeEntity, Integer> {

    Optional<HomeLikeEntity> findByHomeAndProfile(HomeLikeEntity homeLike, ProfileEntity profile);

    @Query("FROM HomeLikeEntity a where  a.home.id =:homeId and a.profile.id =:profileId")
    Optional<HomeLikeEntity> findExists(String homeId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM HomeLikeEntity a where  a.home.id =:homeId and a.profile.id =:profileId")
    void delete(String homeId, Integer profileId);


//    @Transactional
//    @Modifying
    @Query(value = "\n" +
            "select cast(sum(case when status = 'LIKE' then 1 else 0 end) as int) as like_count, " +
            "       cast(sum(case when status = 'DISLIKE' then 1 else 0 end) as int) as dislike_count " +
            "from home_like " +
            "where home_like.home_id =:homeId", nativeQuery = true)
    Map<String, Integer> countLikeDislike(@Param("homeId") String homeId);


}

