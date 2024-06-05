package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.model.response.RolePro;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

//    @Query(value = "select rb.id, rb.name,\n" +
//            "       coalesce(rb.id =\n" +
//            "                (select r.id from role r\n" +
//            "                    join role_authorities ur on r.id = ur.roles_id\n" +
//            "                             where ur.users_id = :userId and r.id=rb.id),\n" +
//            "                false) as hasrole\n" +
//            "            from role rb", nativeQuery = true)
//    List<RolePro> findAllByUserId(Long userId);

    @Query(value = "SELECT r.id, r.name, GROUP_CONCAT(ra.authority) AS authorities " +
            "FROM role r " +
            "LEFT JOIN role_authorities ra ON r.id = ra.role_id " +
            "GROUP BY r.id, r.name", nativeQuery = true)
    List<RolePro> findAllByUserId(Long userId);

}
