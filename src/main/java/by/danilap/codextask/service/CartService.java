package by.danilap.codextask.service;

import by.danilap.codextask.dto.item.ItemDTO;

public interface CartService {

    ItemDTO addItemToCart(Long itemId);

    void removeItemFromCart(Long itemId);

    void purchaseItems();
}
