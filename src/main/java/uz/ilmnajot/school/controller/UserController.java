package uz.ilmnajot.school.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.service.UserService;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAdmin")
    public HttpEntity<ApiResponse> addAdmin(@RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.addAdmin(request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PreAuthorize("hasAuthority('ADD_USER')")
    @PostMapping("/addUser")
    public HttpEntity<ApiResponse> addUser(@RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.addUser(request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping("/getUser/{userId}")
    public HttpEntity<ApiResponse> getUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.getUserById(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_USERS')")
    @GetMapping("/getAllUsers")
    public HttpEntity<ApiResponse> getAllUsers() {
        ApiResponse apiResponse = userService.getAllUsers();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/updateUser/{userId}")
    public HttpEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.updateUser(userId, request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/deleteUser/{userId}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.deleteUser(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping("/getUsers")
    public HttpEntity<ApiResponse> getUsers() {
        ApiResponse apiResponse = userService.getUsers();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping("/getUserByName")
    public HttpEntity<ApiResponse> getUsersByName(@RequestParam(name = "name") String name){
        ApiResponse apiResponse = userService.getUserByName(name);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping("/getUserByEmail")
    public HttpEntity<ApiResponse> getUserByEmail(@RequestParam(name = "email") String email) {
        ApiResponse apiResponse = userService.getUserByEmail(email);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}

