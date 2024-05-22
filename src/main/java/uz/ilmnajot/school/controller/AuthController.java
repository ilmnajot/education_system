package uz.ilmnajot.school.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.LoginForm;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.service.AuthService;
@SecurityRequirement(name = "Bearer")
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/singUp")
    public HttpEntity<ApiResponse> register(@RequestBody UserRequest form) {
        ApiResponse register = authService.register(form);
        return register != null
                ? ResponseEntity.ok(register)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginForm form) {
        ApiResponse authenticate = authService.authenticate(form);
        return authenticate != null
                ? ResponseEntity.ok(authenticate)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
