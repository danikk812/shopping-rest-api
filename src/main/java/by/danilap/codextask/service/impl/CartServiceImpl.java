package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.entity.Item;
import by.danilap.codextask.entity.User;
import by.danilap.codextask.exception.CartIsEmptyException;
import by.danilap.codextask.exception.ItemAlreadyInCartException;
import by.danilap.codextask.exception.ItemNotFoundException;
import by.danilap.codextask.exception.ItemNotInCartException;
import by.danilap.codextask.mapper.ItemMapper;
import by.danilap.codextask.repository.ItemRepository;
import by.danilap.codextask.service.CartService;
import by.danilap.codextask.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static by.danilap.codextask.service.impl.ItemServiceImpl.NO_ITEM_WITH_ID_FOUND;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    public static final String ITEM_ALREADY_IN_CART_EXCEPTION = "Failed to add item to cart, item: %s is already in your cart";
    public static final String ITEM_NOT_IN_CART_EXCEPTION = "Failed to remove item from cart, item: %s is not in your cart";
    public static final String CART_EMPTY_EXCEPTION = "Failed to place order, your cart is empty";


    private final ItemRepository itemRepository;
    private final UserServiceImpl userService;
    private final MailSenderService mailSenderService;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemDTO addItemToCart(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        Item item = itemOptional.orElseThrow(() -> new ItemNotFoundException(
                String.format(NO_ITEM_WITH_ID_FOUND, itemId)));

        User user = userService.getAuthenticatedUser();

        if (user.getUserItems().contains(item)) {
            throw new ItemAlreadyInCartException(
                    String.format(ITEM_ALREADY_IN_CART_EXCEPTION, item.getName()));
        }

        user.getUserItems().add(item);

        return itemMapper.convertToDTO(item);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        Item item = itemOptional.orElseThrow(() -> new ItemNotFoundException(
                String.format(NO_ITEM_WITH_ID_FOUND, itemId)));

        User user = userService.getAuthenticatedUser();

        if (!user.getUserItems().remove(item)) {
            throw new ItemNotInCartException(
                    String.format(ITEM_NOT_IN_CART_EXCEPTION, item.getName()));
        }

    }

    @Override
    @Transactional
    public void purchaseItems() {
        User user = userService.getAuthenticatedUser();

        if (user.getUserItems().size() == 0) {
            throw new CartIsEmptyException(
                    String.format(CART_EMPTY_EXCEPTION));
        }

        mailSenderService.sendPurchaseMessage(user);

        user.getUserItems().clear();
    }
}
