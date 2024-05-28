package uz.ilmnajot.school.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Authority;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.config.AuditingAwareConfig;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

//        Optional<Role> defaultRole = roleRepository.findByName("USER");
//        Role role = defaultRole.orElseThrow(() -> new UserException("there is not found user role", HttpStatus.NOT_FOUND));
        try {
            AuditingAwareConfig.disableAuditing();
            if (mode.equals("always")) {
                User user = userRepository.save(
                        User
                                .builder()
                                .firstName("Elbekjon")
                                .lastName("Umarov")
                                .email("ilmnajot2021@gmail.com")
                                .phoneNumber("+998994107354")
                                .position(Position.TEACHER)
                                .schoolName(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
//                                .roles(Collections.singletonList(role))
                                .roles(null)
                                .gender(Gender.MALE)
                                .password(passwordEncoder.encode("password"))
                                .build());
                Role USER = roleRepository.save(
                        Role
                                .builder()
                                .name("USER")
                                .users(Collections.singletonList(user))
                                .build());

                Role ADMIN = roleRepository.save(
                        Role
                                .builder()
                                .name("ADMIN")
                                .users(Collections.singletonList(user))
                                .build());

            }

        } finally {
            AuditingAwareConfig.enableAuditing();
        }
    }
}
