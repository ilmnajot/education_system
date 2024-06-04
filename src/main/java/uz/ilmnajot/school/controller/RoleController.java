package uz.ilmnajot.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.service.RoleService;
import uz.ilmnajot.school.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final UserService userService;
    private final RoleRepository roleRepository;




    @PreAuthorize("hasAuthority('ADD_USER')")
    @PostMapping("/assignRoleToUser/{roleId}/{userId}")
    public HttpEntity<?> assignRoleToUser(
            @PathVariable(name = "roleId") Long roleId,
            @PathVariable(name = "userId") Long userId){
        System.out.println("assignRoleToUser");
        ApiResponse apiResponse = userService.assignRoleToUser(roleId, userId);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/removeRole/{roleId}/{userId}")
    public HttpEntity<ApiResponse> removeRoleToUser(
            @PathVariable(name = "roleId") Long roleId,
            @PathVariable(name = "userId") Long userId) {
        ApiResponse apiResponse = userService.removeRoleToUser(roleId, userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
