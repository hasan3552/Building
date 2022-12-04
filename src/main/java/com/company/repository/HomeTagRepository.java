package com.company.repository;

import com.company.entity.HomeEntity;
import com.company.entity.HomeTagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeTagRepository extends CrudRepository<HomeTagEntity, Integer> {

    List<HomeTagEntity> findAllByHome(HomeEntity entity);
}
