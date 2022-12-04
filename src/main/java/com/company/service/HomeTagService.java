package com.company.service;

import com.company.dto.tag.TagDTO;
import com.company.entity.HomeEntity;
import com.company.entity.HomeTagEntity;
import com.company.repository.HomeTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeTagService {

    @Autowired
    private HomeTagRepository homeTagRepository;
    @Autowired
    private TagService tagService;

    public void create(HomeEntity home, List<String> tagList) {

        tagList.forEach(tagName -> {
            HomeTagEntity homeTagEntity = new HomeTagEntity();
            homeTagEntity.setHome(home);
            homeTagEntity.setTag(tagService.createdIfNotExist(tagName));
            homeTagRepository.save(homeTagEntity);
        });
    }

    public List<TagDTO> getTagListByArticle(HomeEntity home) {

        List<HomeTagEntity> list = homeTagRepository.findAllByHome(home);

        List<TagDTO> tagDTOList = new ArrayList<>();
        list.forEach(homeTagEntity -> {
            TagDTO tagDTO = tagService.getTagDTO(homeTagEntity.getTag());
            tagDTOList.add(tagDTO);
        });

        return tagDTOList;
    }
}
