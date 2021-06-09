package by.danilap.codextask.controller;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;
import by.danilap.codextask.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Api(description = "Controller create, read and delete tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @ApiOperation(value = "Get tags")
    public ResponseEntity<List<TagDTO>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @PostMapping
    @ApiOperation(value = "Create new tag")
    public ResponseEntity<TagDTO> newTag(@Valid @RequestBody TagRequestDTO tagRequestDTO) {
        return new ResponseEntity<>(tagService.createTag(tagRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get tag by id")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

}
