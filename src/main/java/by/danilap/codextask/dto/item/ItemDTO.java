package by.danilap.codextask.dto.item;

import by.danilap.codextask.dto.tag.TagDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "item1")
    private String name;

    @ApiModelProperty(example = "description of item1")
    private String description;

    private List<String> tags;

}
