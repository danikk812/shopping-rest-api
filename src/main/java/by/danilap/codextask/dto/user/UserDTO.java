package by.danilap.codextask.dto.user;

import by.danilap.codextask.dto.item.ItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "john")
    private String nickname;

    @ApiModelProperty(example = "john@gmail.com")
    private String email;

    private List<ItemDTO> items;
}
