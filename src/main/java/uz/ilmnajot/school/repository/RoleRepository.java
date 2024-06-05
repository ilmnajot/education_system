package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.model.project.RolePro;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query(value = "SELECT r.id, r.name, " +
            "COALESCE(" +
            "  (SELECT COUNT(*) FROM users u " +
            "   WHERE u.role_id = r.id AND u.id = :userId" +
            "  ) > 0, false" +
            ") AS hasRole " +
            "FROM role r", nativeQuery = true)
    List<RolePro> findAllByUserId(@Param("userId")Long userId);

}
