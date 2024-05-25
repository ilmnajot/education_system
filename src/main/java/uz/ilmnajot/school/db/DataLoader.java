package uz.ilmnajot.school.db;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.RoleName;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.config.AuditingAwareConfig;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
      try {
          AuditingAwareConfig.disableAuditing();
          if (mode.equals("always")) {
              userRepository.save(
                      User
                              .builder()
                              .firstName("Elbekjon")
                              .lastName("Umarov")
                              .email("ilmnajot2021@gmail.com")
                              .phoneNumber("+998994107354")
                              .position("Teacher")
                              .schoolName(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
                              .roleName(RoleName.USER)
                              .gender(Gender.MALE)
                              .password(passwordEncoder.encode("password"))
                              .build());

          }

      }
      finally {
          AuditingAwareConfig.enableAuditing();
      }
    }
}
