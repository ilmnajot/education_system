package uz.ilmnajot.school.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.school.entity.Course;
import uz.ilmnajot.school.entity.News;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Authority;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.repository.CourseRepository;
import uz.ilmnajot.school.repository.NewsRepository;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.config.AuditingAwareConfig;
import uz.ilmnajot.school.utils.AppConstants;

import java.util.Arrays;

import static uz.ilmnajot.school.enums.Authority.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final NewsRepository newsRepository;

    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {

        try {
            AuditingAwareConfig.disableAuditing();
            if (mode.equals("always")) {

                Authority[] authorities = Authority.values();

                Role super_admin = roleRepository.save(new Role(
                        AppConstants.SUPER_ADMIN,
                        Arrays.asList(authorities)
                ));

                Role admin = roleRepository.save(new Role(
                        AppConstants.ADMIN,
                        Arrays.asList(
                                ADD_USER,
                                GET_USER,
                                GET_USERS,
                                DELETE_USER,
                                UPDATE_USER,
                                ADD_NEWS,
                                DELETE_NEWS,
                                UPDATE_NEWS, ADD_COURSE,
                                ADD_USER_TO_COURSE,
                                DELETE_USER_FROM_COURSE,
                                DELETE_COURSE,
                                UPDATE_COURSE,

                                GET_COURSE,
                                GET_COURSE_BY_STUDENT_ID,
                                GET_STUDENTS_BY_COURSE_ID,
                                GET_ALL_COURSE

                        )));

                Role user = roleRepository.save(new Role(
                        AppConstants.USER,
                        Arrays.asList(
                                GET_USER,
                                GET_USERS,
                                GET_NEWS,
                                GET_ALL_NEWS,
                                GET_COURSE,
                                GET_ALL_COURSE)
                ));
                userRepository.save(
                        User
                                .builder()
                                .firstName("super_admin")
                                .lastName("admin_super")
                                .email("ilmnajot2023@gmail.com")
                                .phoneNumber("+998994107356")
                                .position(Position.TEACHER)
                                .schoolName(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
                                .role(super_admin)
                                .gender(Gender.MALE)
                                .password(passwordEncoder.encode("password"))
                                .build());

                userRepository.save(
                        User
                                .builder()
                                .firstName("Elbekjon")
                                .lastName("Umarov")
                                .email("ilmnajot2025@gmail.com")
                                .phoneNumber("+998994107355")
                                .position(Position.TEACHER)
                                .schoolName(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
                                .role(admin)
                                .gender(Gender.MALE)
                                .password(passwordEncoder.encode("password"))
                                .build());
                userRepository.save(
                        User
                                .builder()
                                .firstName("Elbekjon")
                                .lastName("Umarov")
                                .email("ilmnajot2024@gmail.com")
                                .phoneNumber("+998994107354")
                                .position(Position.TEACHER)
                                .schoolName(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
                                .role(user)
                                .gender(Gender.MALE)
                                .password(passwordEncoder.encode("password"))
                                .build());

                newsRepository.save(
                        News
                                .builder()
                                .title("title here")
                                .content("content here")
                                .images(null)
                                .publishedDate(null)
                                .author("author")
                                .build());


                courseRepository.save(
                        Course
                                .builder()
                                .name("Maths")
                                .description("Maths descriptions here")
                                .instructor("Elbek_Umarov")
                                .imageUrl("image_url here")
                                .durationInHours(2.0)
                                .build()
                );
            }


        } finally {
            AuditingAwareConfig.enableAuditing();
        }
    }
}
