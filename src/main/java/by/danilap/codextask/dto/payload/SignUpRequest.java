package by.danilap.codextask.dto.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @Size(min =  3, max = 45)
    @ApiModelProperty(example = "john")
    private String nickname;

    @Size(min = 3, max = 30)
    @ApiModelProperty(example = "doe123")
    private String password;

    @NotBlank
    @Email
    @ApiModelProperty(example = "john@gmail.com")
    private String email;


}
