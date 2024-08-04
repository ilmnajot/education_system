package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.entity.test.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
