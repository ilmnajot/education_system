package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.Query;
import uz.ilmnajot.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.enums.SchoolName;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "select * from users  where users.email like %?1%", nativeQuery = true)
    Optional<User> findByEmail(/*@Param("email") */String username);

    @Query(value = "SELECT * FROM user WHERE users.fullName LIKE %?1%", nativeQuery = true)
    Optional<User> findByFullName(String fullName);

    Optional<User> findBySchoolName(SchoolName schoolName);
}
