package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ilmnajot.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.model.response.UserPro;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "select * from users  where users.email like %?1%", nativeQuery = true)
    Optional<User> findByEmail(/*@Param("email") */String username);

//
//    @Query(value = "select * from users where users.first_name like  %?1% OR  users.last_name like %?1%", nativeQuery = true )
//    List<User> findByPartialNameOrLastName(@Param("name") String name);

    @Query(value = "SELECT * FROM users WHERE " +
            "LOWER(users.first_name) LIKE " +
            "LOWER(CONCAT('%', ?1, '%')) OR " +
            "LOWER(users.last_name) LIKE " +
            "LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<User> findByPartialNameOrLastName(@Param("name") String name);



    @Query(value = "select id, " +
            "first_name as firstName, " +
            "last_name as lastName, " +
            "email, " +
            "gender," +
            "phone_number as phoneNumber from users", nativeQuery = true)
    List<UserPro> findAllByUsers();


    Optional<User> findBySchoolName(SchoolName schoolName);
}
