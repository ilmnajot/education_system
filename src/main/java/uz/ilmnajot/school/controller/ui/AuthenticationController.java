package uz.ilmnajot.school.controller.ui;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.service.AuthService;
@Controller
@RequestMapping("/auth")
@CrossOrigin
@SecurityRequirement(name="Bearer")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        UserRequest user = new UserRequest();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(UserRequest userRequest, Model model){
        ApiResponse user = authService.register(userRequest);
        model.addAttribute("register", user);
        return "redirect:/auth/login";

    }

}



