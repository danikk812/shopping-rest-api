package by.danilap.codextask.dto.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {

    @NotBlank
    @ApiModelProperty(example = "john")
    private String nickname;

    @NotBlank
    @ApiModelProperty(example = "doe123")
    private String password;

}
