package uz.ilmnajot.school.service;

import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.RoleName;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.LoginForm;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.model.response.LoginResponse;
import uz.ilmnajot.school.model.response.UserResponse;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.jwt.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, UserServiceImpl userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     *
     * @param request
     * @return user details and generated token
     */

    @Override
    public ApiResponse register(UserRequest request) {
        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
        if (userByEmail.isPresent()) {
            throw new UserException("User is already exist", HttpStatus.CONFLICT);
        }
        if (!checkPassword(request)) {
            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoleName(RoleName.ADMIN);
        user.setGender(request.getGender());
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        String token = jwtProvider.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("The User with username: " + request.getUsername() + " and user details are : " + userResponse + " has been registered successfully", true, loginResponse);
    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }


    @Override
    public ApiResponse authenticate(LoginForm form) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                form.getUsername(),
                form.getPassword()
        ));
        var user = userRepository.findByUsername(form.getUsername()).orElseThrow();
        String token = jwtProvider.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return new ApiResponse("the user with username " + form.getUsername() + " has been authenticated and Token has been generated successfully", true, loginResponse);
    }
}
