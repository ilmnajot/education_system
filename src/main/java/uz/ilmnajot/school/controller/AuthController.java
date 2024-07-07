package uz.ilmnajot.school.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.LoginForm;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.service.AuthService;

import static uz.ilmnajot.school.utils.AppConstants.SIGNIN;
import static uz.ilmnajot.school.utils.AppConstants.SIGNUP;


@SecurityRequirement(name = "Bearer")
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public HttpEntity<?> test(@RequestBody UserRequest form){
        System.out.println(form);
        return ResponseEntity.ok("test");
    }

//    @PreAuthorize("hasAuthority('ADD_USER')")
    @PostMapping(SIGNUP)
    public HttpEntity<ApiResponse> register(@RequestBody UserRequest form) {
        ApiResponse register = authService.register(form);
        return register != null
                ? ResponseEntity.ok(register)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

//    @PreAuthorize("hasRole('ADD_USER')")
    @PostMapping(SIGNIN)
    public ResponseEntity<ApiResponse> login(@RequestBody LoginForm form) {
        ApiResponse authenticate = authService.authenticate(form);
        return authenticate != null
                ? ResponseEntity.ok(authenticate)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
