package by.danilap.codextask.dto.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TagDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "tag1")
    private String name;
}
