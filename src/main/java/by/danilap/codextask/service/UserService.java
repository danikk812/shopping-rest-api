package by.danilap.codextask.service;

import by.danilap.codextask.dto.payload.SignUpRequest;
import by.danilap.codextask.dto.user.UserDTO;

public interface UserService {

    UserDTO getUser(Long id);

    UserDTO signUp(SignUpRequest signUpRequest);
}
