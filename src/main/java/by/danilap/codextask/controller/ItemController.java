package by.danilap.codextask.controller;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.service.ItemService;
import by.danilap.codextask.service.TagService;
import by.danilap.codextask.util.filter.ItemQueryParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Api(description = "Controller to create, update and delete products")
public class ItemController {

    private final ItemService itemService;
    private final TagService tagService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get item by id")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get items")
    public ResponseEntity<List<ItemDTO>> getItemByParams(@Valid @RequestBody ItemQueryParameter parameter) {
        return ResponseEntity.ok(itemService.getItems(parameter));
    }

    @PostMapping
    @ApiOperation(value = "Create new item")
    public ResponseEntity<ItemDTO> newItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        return new ResponseEntity<>(itemService.createItem(itemRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update item")
    public ResponseEntity<ItemDTO> updateItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO, @PathVariable Long id) {
        return ResponseEntity.ok(itemService.updateItem(itemRequestDTO, id, false));
    }

    @PutMapping("/force/{id}")
    @ApiOperation(value = "Force update item from the user's cart")
    public ResponseEntity<ItemDTO> forceUpdateItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(itemService.updateItem(itemRequestDTO, id, true));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete item")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
