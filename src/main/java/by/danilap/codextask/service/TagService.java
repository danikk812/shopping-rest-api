package by.danilap.codextask.service;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;

import java.util.List;

public interface TagService {

    TagDTO createTag(TagRequestDTO tagRequestDTO);

    void deleteTag(Long id);

    TagDTO getTagById(Long id);

    List<TagDTO> getTags();
}
