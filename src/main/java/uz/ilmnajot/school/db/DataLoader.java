package uz.ilmnajot.school.db;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            userRepository.save(
                    User
                            .builder()
                            .fullName("Elbekjon")
                            .username("ilmnajot2021@gmail.com")
                            .phoneNumber("+998994107354")
                            .password(passwordEncoder.encode("password"))
                            .build());

        }
    }
}
