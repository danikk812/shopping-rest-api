package by.danilap.codextask.service;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.util.filter.ItemQueryParameter;

import java.util.List;

public interface ItemService {

    ItemDTO createItem(ItemRequestDTO itemRequestDTO);

    ItemDTO updateItem(ItemRequestDTO itemRequestDTO, Long id, boolean isForced);

    void deleteItem(Long id);

    ItemDTO getItemById(Long id);

    List<ItemDTO> getItems();

    List<ItemDTO> getItems(ItemQueryParameter itemQueryParameter);
}
