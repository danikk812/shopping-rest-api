package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;
import by.danilap.codextask.entity.Tag;
import by.danilap.codextask.exception.TagAlreadyExistsException;
import by.danilap.codextask.exception.TagNotFoundException;
import by.danilap.codextask.mapper.TagMapper;
import by.danilap.codextask.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final long TEST_ID = 1;
    private static final String TEST_NAME = "Tag";

    @InjectMocks
    private TagServiceImpl tagService;

    @Autowired
    private TagMapper tagMapper;

    @Mock
    private TagRepository tagRepository;

    private Tag tag;
    private TagDTO testTagDto;
    private TagRequestDTO testForCreationTagDto;


    @BeforeEach
    public void setUp() {

        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);


        testTagDto = new TagDTO();
        testTagDto.setName(TEST_NAME);
        testTagDto.setId(TEST_ID);

        testForCreationTagDto = new TagRequestDTO();
        testForCreationTagDto.setName(TEST_NAME);


        tagService = new TagServiceImpl(tagRepository, tagMapper);
    }

    @Test
    public void deleteTag() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.of(tag));

        tagService.deleteTag(TEST_ID);

        verify(tagRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteTagShouldException() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(TEST_ID));
    }


    @Test
    public void getTagByIDShouldException() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.getTagById(TEST_ID));
    }


    @Test
    public void createTagShouldAlreadyExistsException() {
        given(tagRepository.findByName(any())).willReturn(Optional.of(tag));

        assertThrows(TagAlreadyExistsException.class,
                () -> tagService.createTag(testForCreationTagDto));
    }
}