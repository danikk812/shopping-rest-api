package by.danilap.codextask.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String nickname;
    private List<String> roles;
}
