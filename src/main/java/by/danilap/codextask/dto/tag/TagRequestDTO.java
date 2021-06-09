package by.danilap.codextask.dto.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagRequestDTO {

    @NotBlank
    @ApiModelProperty(example = "tag1")
    private String name;
}
