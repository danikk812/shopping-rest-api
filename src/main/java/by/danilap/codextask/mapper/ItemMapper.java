package by.danilap.codextask.mapper;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.entity.Item;
import by.danilap.codextask.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final ModelMapper modelMapper;

    public Item convertToEntity(ItemRequestDTO itemRequestDTO) {
        return modelMapper.map(itemRequestDTO, Item.class);
    }

    public ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        List<Tag> tagList = item.getTags();
        if (tagList != null) {
            List<String> tagNamesList = new ArrayList<>();

            tagList.forEach(tag -> tagNamesList.add(tag.getName()));
            itemDTO.setTags(tagNamesList);
        }
        return itemDTO;
    }

}
