package uz.ilmnajot.school.service;

import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;

public interface UserService {

//    LoginResponse addUser(UserRequest request);

    ApiResponse getUserById(Long userId);

    ApiResponse getUsers();

    ApiResponse updateUser(Long userId, UserRequest request);

    ApiResponse deleteUser(Long userId);

    ApiResponse getUserByName(String username);

    ApiResponse getUserByEmail(String email);

    ApiResponse addUser(UserRequest request);

    ApiResponse assignRoleToUser(Long roleId, Long userId);

    ApiResponse removeRoleToUser(Long roleId, Long userId);

    ApiResponse addAdmin(UserRequest request);

//    LoginResponse addUser(UserRequest request);
}
