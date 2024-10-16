package uz.ilmnajot.school.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/test")
    public HttpEntity<?> test(@RequestBody UserRequest form){
        System.out.println(form);
        return ResponseEntity.ok("test");
    }

//    @PreAuthorize("hasAuthority('ADD_USER')")
//    @PostMapping(SIGNUP)
//    public HttpEntity<ApiResponse> register(@RequestBody UserRequest form) {
//        ApiResponse register = authService.register(form);
//        return register != null
//                ? ResponseEntity.ok(register)
//                : ResponseEntity.status(HttpStatus.CONFLICT).build();
//    }
//
////    @PreAuthorize("hasRole('ADD_USER')")
//    @PostMapping(SIGNIN)
//    public ResponseEntity<ApiResponse> login(@RequestBody LoginForm form) {
//        ApiResponse authenticate = authService.authenticate(form);
//        return authenticate != null
//                ? ResponseEntity.ok(authenticate)
//                : ResponseEntity.status(HttpStatus.CONFLICT).build();
//    }

}
