package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.entity.Item;
import by.danilap.codextask.entity.Tag;
import by.danilap.codextask.exception.ItemNotFoundException;
import by.danilap.codextask.mapper.ItemMapper;
import by.danilap.codextask.repository.ItemRepository;
import by.danilap.codextask.repository.TagRepository;
import by.danilap.codextask.service.MailSenderService;
import by.danilap.codextask.util.filter.ItemQueryParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    private static final long TEST_ID = 1;
    private static final long TEST_TAG_ID = 2;
    private static final String TEST_TAG_NAME = "Test tag";
    private static final String TEST_NAME = "Prod";
    private static final String TEST_DESCRIPTION = "product";
    private static final String TEST_NEW_NAME = "New name";
    private static final String TEST_NEW_DESCRIPTION = "New description";

    @InjectMocks
    private ItemServiceImpl itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private MailSenderService mailSenderService;

    private Tag testTag;
    private Item item;
    private ItemDTO notFullyValuedItemDto;
    private ItemRequestDTO dtoWithoutID;
    private ItemDTO testItemDto;
    private ItemQueryParameter emptyQueryParameter;
    private ItemQueryParameter queryParameter;

    private List<String> tagNamesList;
    private List<Tag> itemTagList;
    private List<Item> itemList;
    private List<ItemDTO> testItemDTOList;

    @BeforeEach
    public void setUp() {

        testTag = new Tag();
        testTag.setId(TEST_TAG_ID);
        testTag.setName(TEST_TAG_NAME);

        itemTagList = new ArrayList<>();
        itemTagList.add(testTag);

        item = new Item();
        item.setId(TEST_ID);
        item.setName(TEST_NAME);
        item.setDescription(TEST_DESCRIPTION);
        item.setTags(itemTagList);

        notFullyValuedItemDto = new ItemDTO();
        notFullyValuedItemDto.setName(TEST_NEW_NAME);
        notFullyValuedItemDto.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        testItemDto = new ItemDTO();
        testItemDto.setId(TEST_ID);
        testItemDto.setName(TEST_NAME);
        testItemDto.setDescription(TEST_DESCRIPTION);
        testItemDto.setTags(tagNamesList);

        dtoWithoutID = new ItemRequestDTO();
        dtoWithoutID.setName(TEST_NAME);
        dtoWithoutID.setDescription(TEST_DESCRIPTION);
        dtoWithoutID.setTags(tagNamesList);

        itemList = new ArrayList<>();
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);

        emptyQueryParameter = new ItemQueryParameter();
        queryParameter = new ItemQueryParameter();

        queryParameter.setDescription(TEST_NEW_DESCRIPTION);
        queryParameter.setTagName(tagNamesList);

        itemService = new ItemServiceImpl(itemRepository, tagRepository, mailSenderService, itemMapper);
    }

    @Test
    public void deleteItem() {
        given(itemRepository.findById(TEST_ID)).willReturn(Optional.of(item));

        itemService.deleteItem(TEST_ID);

        verify(itemRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteItemShouldException() {
        given(itemRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(TEST_ID));
    }

    @Test
    public void getGiftItemByIDShouldException() {
        given(itemRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(TEST_ID));
    }


    @Test
    public void updateItemShouldException() {
        given(itemRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> itemService.updateItem(dtoWithoutID, TEST_ID, false));
    }


}