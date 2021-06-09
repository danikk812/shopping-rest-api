package by.danilap.codextask.controller;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Api(description = "Shopping cart controller to add, remove and purchase items from the cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ApiOperation(value = "Add item to the cart")
    public ResponseEntity<ItemDTO> addItemToCart(@RequestBody Long itemId) {
        return ResponseEntity.ok(cartService.addItemToCart(itemId));
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove item from the cart")
    public void removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @PutMapping("/purchase")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Purchase all items from the cart")
    public void purchaseItems() {
        cartService.purchaseItems();
    }
}
