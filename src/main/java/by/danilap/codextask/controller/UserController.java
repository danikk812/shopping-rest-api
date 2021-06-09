package by.danilap.codextask.controller;

import by.danilap.codextask.dto.payload.AuthRequest;
import by.danilap.codextask.dto.payload.AuthResponse;
import by.danilap.codextask.dto.payload.SignUpRequest;
import by.danilap.codextask.dto.user.UserDTO;
import by.danilap.codextask.security.UserPrincipal;
import by.danilap.codextask.security.jwt.JwtProvider;
import by.danilap.codextask.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(description = "Controller to register and authenticate user")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/users/new")
    @ApiOperation(value = "Register user")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(userService.signUp(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Authenticate user")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getNickname(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponse(token, userPrincipal.getUsername(), roles));
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get user by id")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
