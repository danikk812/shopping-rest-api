package by.danilap.codextask.dto.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ItemRequestDTO {

    private Long id;

    @NotBlank
    @ApiModelProperty(example = "item1")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "description of item1")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> tags;
}
