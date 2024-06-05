package uz.ilmnajot.school.service.impl;

import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.LoginForm;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.model.response.LoginResponse;
import uz.ilmnajot.school.model.response.UserResponse;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.config.AuditingAwareConfig;
import uz.ilmnajot.school.security.jwt.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.service.AuthService;
import uz.ilmnajot.school.utils.AppConstants;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, UserServiceImpl userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository, RoleRepository roleRepository1) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository1;
    }

    @Override
    public ApiResponse register(UserRequest request) {

        Optional<Role> defaultRole = roleRepository.findByName(AppConstants.USER);
        Role role = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));

        Optional<User> userByEmail = userRepository.findByEmail(request.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserException("User is already exist", HttpStatus.CONFLICT);
        }

        if (!checkPassword(request)) {
            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
        }
        try {
            AuditingAwareConfig.disableAuditing();
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPosition(request.getPosition());
            user.setSchoolName(request.getSchoolName());
            user.setGender(request.getGender());
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEnabled(true);
            User savedUser = userRepository.save(user);
            String token = jwtProvider.generateToken(user);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
            System.out.println("token: " + token);
            return new ApiResponse("The User with username: " + request.getEmail() + " and user details are : " + userResponse + " has been registered successfully", true, loginResponse);
        } finally {
            AuditingAwareConfig.enableAuditing();
        }
    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }


    @Override
    public ApiResponse authenticate(LoginForm form) {

        try {
            AuditingAwareConfig.enableAuditing();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    form.getEmail(),
                    form.getPassword()
            ));

            var user = userRepository.findByEmail(form.getEmail()).orElseThrow();
            String token = jwtProvider.generateToken(user);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            return new ApiResponse("the user with username " + form.getEmail() + " has been authenticated and Token has been generated successfully", true, loginResponse);
        } finally {
            AuditingAwareConfig.enableAuditing();
        }
    }
}
