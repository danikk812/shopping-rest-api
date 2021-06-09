package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.payload.SignUpRequest;
import by.danilap.codextask.dto.user.UserDTO;
import by.danilap.codextask.entity.Role;
import by.danilap.codextask.entity.User;
import by.danilap.codextask.exception.UserAlreadyExistsException;
import by.danilap.codextask.exception.UserNotFoundException;
import by.danilap.codextask.mapper.UserMapper;
import by.danilap.codextask.repository.UserRepository;
import by.danilap.codextask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String NO_USER_WITH_ID_FOUND = "No user with id: %d found";

    public static final String NO_USER_WITH_NICKNAME_FOUND = "No user with nickname: %s found";

    public static final String USER_BY_NICKNAME_ALREADY_EXISTS = "User with nickname: %s already exists";


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(
                String.format(NO_USER_WITH_ID_FOUND, id )));

        return userMapper.convertToDTO(user);
    }

    @Override
    @Transactional
    public UserDTO signUp(SignUpRequest signUpRequest) {
        Optional<User> userByNickname = userRepository.findByNickname(signUpRequest.getNickname());
        if (userByNickname.isPresent()) {
            throw new UserAlreadyExistsException(
                    String.format(USER_BY_NICKNAME_ALREADY_EXISTS, signUpRequest.getNickname()));
        }

        User user = new User();
        user.setNickname(signUpRequest.getNickname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        return userMapper.convertToDTO(userRepository.save(user));
    }

    public User getAuthenticatedUser() {
        String nickname;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            nickname = ((UserDetails) principal).getUsername();
        } else {
            nickname = principal.toString();
        }

        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(NO_USER_WITH_NICKNAME_FOUND, nickname)));
    }
}
