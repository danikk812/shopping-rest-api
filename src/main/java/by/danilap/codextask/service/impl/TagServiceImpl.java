package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;
import by.danilap.codextask.entity.Tag;
import by.danilap.codextask.exception.TagAlreadyExistsException;
import by.danilap.codextask.exception.TagNotFoundException;
import by.danilap.codextask.mapper.TagMapper;
import by.danilap.codextask.repository.TagRepository;
import by.danilap.codextask.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    public static final String TAG_ALREADY_EXISTS_EXCEPTION = "Tag with name: %s already exists";
    public static final String NO_TAG_WITH_ID_FOUND = "No tag with id: %d found";

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public TagDTO createTag(TagRequestDTO tagRequestDTO) {
        String tagName = tagRequestDTO.getName();

        Optional<Tag> tagByName = tagRepository.findByName(tagRequestDTO.getName());

        if (tagByName.isPresent()) {
            throw new TagAlreadyExistsException(
                    String.format(TAG_ALREADY_EXISTS_EXCEPTION, tagRequestDTO.getName()));
        }

        Tag tag = tagRepository.save(tagMapper.convertToEntity(tagRequestDTO));

        return tagMapper.convertToDTO(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        if (!tagRepository.findById(id).isPresent()) {
            throw new TagNotFoundException(
                    String.format(NO_TAG_WITH_ID_FOUND, id));
        }

        tagRepository.deleteById(id);
    }

    @Override
    public TagDTO getTagById(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);

        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                String.format(NO_TAG_WITH_ID_FOUND, id)));

        return tagMapper.convertToDTO(tag);
    }

    @Override
    public List<TagDTO> getTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
