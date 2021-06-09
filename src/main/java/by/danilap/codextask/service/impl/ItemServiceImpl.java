package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.entity.Item;
import by.danilap.codextask.entity.Tag;
import by.danilap.codextask.entity.User;
import by.danilap.codextask.exception.ItemIsInCartException;
import by.danilap.codextask.exception.ItemNotFoundException;
import by.danilap.codextask.mapper.ItemMapper;
import by.danilap.codextask.repository.ItemRepository;
import by.danilap.codextask.repository.TagRepository;
import by.danilap.codextask.service.ItemService;
import by.danilap.codextask.service.MailSenderService;
import by.danilap.codextask.util.filter.ItemQueryParameter;
import by.danilap.codextask.util.filter.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    public static final String NO_ITEM_WITH_ID_FOUND = "No item with id: %d found";
    public static final String ITEM_IN_CART_EXCEPTION = "Failed to update, item with id: %d is in user cart";

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final MailSenderService mailSenderService;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemDTO createItem(ItemRequestDTO itemRequestDTO) {
        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(itemRequestDTO.getTags());

        Item item = itemMapper.convertToEntity(itemRequestDTO);
        item.setTags(tagList);

        Item newItem = itemRepository.save(item);

        return itemMapper.convertToDTO(newItem);
    }

    @Override
    @Transactional
    public ItemDTO updateItem(ItemRequestDTO itemRequestDTO, Long id, boolean isForced) {
        Item itemFromDB = itemRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException(String.format(NO_ITEM_WITH_ID_FOUND, id)));

        if (!isForced && itemFromDB.getBuyers().size() > 0) {
            throw new ItemIsInCartException(
                    String.format(ITEM_IN_CART_EXCEPTION, id));
        }

        itemRequestDTO.setId(id);

        Item itemFromDTO = itemMapper.convertToEntity(itemRequestDTO);

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(itemRequestDTO.getTags());
        itemFromDTO.setTags(tagList);

        Item item = updateFields(itemFromDTO, itemFromDB);

        Item updatedItem = itemRepository.save(item);

        for (User buyer : itemFromDB.getBuyers()) {
            mailSenderService.sendItemUpdateMessage(buyer, itemMapper.convertToDTO(itemFromDB),
                    itemMapper.convertToDTO(updatedItem));
        }

        return itemMapper.convertToDTO(updatedItem);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.findById(id).isPresent()) {
            throw new ItemNotFoundException(
                    String.format(NO_ITEM_WITH_ID_FOUND, id));
        }

        itemRepository.deleteById(id);
    }

    @Override
    public ItemDTO getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        Item item = optionalItem.orElseThrow(() ->
                new ItemNotFoundException(
                        String.format(NO_ITEM_WITH_ID_FOUND, id)));

        return itemMapper.convertToDTO(item);
    }

    @Override
    public List<ItemDTO> getItems() {
        return itemRepository.findAll().stream()
                .map(itemMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDTO> getItems(ItemQueryParameter itemQueryParameter) {
        if (itemQueryParameter.isEmpty()) {
            return getItems();
        }

        List<Item> itemList = itemRepository.findAll(ItemSpecification.findByQueryParameter(
                itemQueryParameter));

        return itemList.stream()
                .map(itemMapper::convertToDTO)
                .collect(Collectors.toList());
    }


    private List<Tag> createTagsIfNotFoundAndReturnAll(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return new ArrayList<>();
        }
        List<Tag> tagList = new ArrayList<>();

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagRepository.findByName(tagName);

            Tag tagForSave = new Tag();
            tagForSave.setName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagRepository.save(tagForSave));
            tagList.add(tag);
        });

        return tagList;
    }

    private Item updateFields(Item dataFrom, Item dataTo) {
        String name = dataFrom.getName();
        if (name != null) {
            dataTo.setName(name);
        }

        String desc = dataFrom.getDescription();
        if (desc != null) {
            dataTo.setDescription(desc);
        }

        List<Tag> tagList = dataFrom.getTags();
        if (tagList != null) {
            dataTo.setTags(tagList);
        }
        return dataTo;
    }
}
