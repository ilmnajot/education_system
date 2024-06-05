package uz.ilmnajot.school.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.model.response.UserPro;
import uz.ilmnajot.school.model.response.UserResponse;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;
import static uz.ilmnajot.school.utils.AppConstants.USER;

@Service
public class UserServiceImpl implements UserService {

//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

//
//    @Override
//    public LoginResponse addUser(UserRequest request) {
//        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
//        if (userByEmail.isPresent()) {
//            throw new UserException("User is already exist", HttpStatus.CONFLICT);
//        }
//        if (!checkPassword(request)) {
//            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
//        }
//        User user = new User();
//        user.setFullName(request.getFullName());
//        user.setUsername(request.getUsername());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRoleName(RoleName.ADMIN);
//        user.setEnabled(true);
//        userRepository.save(user);
//        String token = jwtProvider.generateToken(user);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(token);
////        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
//        return loginResponse;
//    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }

    @Override
    public ApiResponse getUserById(Long userId) {
        User user = getUser(userId);
//        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        UserResponse userResponse = new UserResponse();
        UserResponse response = userResponse.userToDto(user);
        return new ApiResponse("User Found", true, response);
    }

    @Override
    public ApiResponse getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> responseList = users
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("User response", responseList);
        responseMap.put("current page", 1);
        responseMap.put("totalItems", responseList.size());
        return new ApiResponse("Users Found", true, responseMap);
    }

    @Override
    public ApiResponse updateUser(Long userId, UserRequest request) {
//        if (!isAdminOrSuperAdmin()) {
//            throw new UserException("SORRY, YOU DO NOT HAVE PERMISSION TO ADD NEW USER HERE", HttpStatus.BAD_REQUEST);
//        }
        Role role;
        Optional<Role> defaultRole = roleRepository.findByName(USER);
        role = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));
        Long roleId = role.getId();
        if (!userRepository.existsById(userId)) {
            throw new UserException("no user found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        User user = getUser(userId);
        user.setId(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosition(request.getPosition());
        user.setSchoolName(request.getSchoolName());
        user.setGender(request.getGender());
        user.setRoleId(roleId);
        User savedUser = userRepository.save(user);
        UserResponse userResponse1 = new UserResponse();
        UserResponse userResponse2 = userResponse1.userToDto(savedUser);
//        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("User Updated", true, userResponse2);
    }

    @Override
    public ApiResponse deleteUser(Long userId) {
        getUser(userId);
        userRepository.deleteById(userId);
        return new ApiResponse("user deleted", true, "User deleted successfully");
    }

    @Override
    public ApiResponse getAllUsers() {
        List<UserPro> users = userRepository.findAllByUsers();
        if (users.isEmpty()) {
            throw new UserException("users not found", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse("USERS FOUND", true, users);
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
//            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            UserResponse userResponse = new UserResponse();
            UserResponse userResponse1 = userResponse.userToDto(user);
            return new ApiResponse("USER FOUND", true, userResponse1);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }


    @Override
    public ApiResponse addUser(UserRequest request) {
//        if (!isAdminOrSuperAdmin()) {
//            throw new UserException("SORRY, YOU DO NOT HAVE PERMISSION TO ADD NEW USER HERE", HttpStatus.BAD_REQUEST);
//        }
        Role role;
        Optional<Role> defaultRole = roleRepository.findByName(USER);
        role = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));
        Long roleId = role.getId();
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosition(request.getPosition());
        user.setSchoolName(request.getSchoolName());
        user.setGender(request.getGender());
        user.setRoleId(roleId);
        User savedUser = userRepository.save(user);
        UserResponse userResponse1 = new UserResponse();
        UserResponse userResponse2 = userResponse1.userToDto(savedUser);
//        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("success", true, userResponse2);

    }
//
//    private boolean isAdminOrSuperAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if ("ADD_USER".equals(authority.getAuthority()) || "DELETE_ROLE".equals(authority.getAuthority())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    @Override
    public ApiResponse addAdmin(UserRequest request) {
        Role role;
        Optional<Role> defaultRole = roleRepository.findByName("ADMIN");
        role = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
//        User user = getUser(request, role);
//        User savedUser = userRepository.save(user);

//        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
//        return new ApiResponse("success", true, userResponse);
        return null;
    }

    @Override
    public ApiResponse getUserByName(String name) {
        List<User> optionalUser = userRepository.findByPartialNameOrLastName(name);

        if (optionalUser.isEmpty()) {
            throw new UserException("user not found", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> responseList = optionalUser
                .stream()
                .map(user1 -> new UserResponse().userToDto(user1))
                .toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("User response", responseList);
        responseMap.put("current page", 1);
        responseMap.put("totalItems", responseList.size());
        return new ApiResponse("Users Found", true, responseMap);

    }

    @Override
    public ApiResponse assignRoleToUser(Long roleId, Long userId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalRole.isPresent() && optionalUser.isPresent()) {
            Role role = optionalRole.get();
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            return new ApiResponse("success", true, "role has been saved successfully. role name: " + user.getRole().getName());
        }
        throw new UserException("there is no existing role or user with id" + userId + " and  " + roleId);
    }

    @Override
    public ApiResponse removeRoleToUser(Long roleId, Long userId) {
        Role role_user;
        Optional<Role> defaultRole = roleRepository.findByName(USER);
        role_user = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalRole.isPresent() && optionalUser.isPresent()) {
            Role role = optionalRole.get();
            User user = optionalUser.get();

            if (user.getRole().equals(role)) {
                user.setRole(role_user);
            }
            userRepository.save(user);
            return new ApiResponse("success", true, "roleName: " + role.getName() + " has been removed successfully and has been set " + role_user.getName() + " to the user id " + user.getId());
        }
        throw new UserException("there is no existing role or user with id" + userId + " and  " + roleId);
    }


    private User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

}
