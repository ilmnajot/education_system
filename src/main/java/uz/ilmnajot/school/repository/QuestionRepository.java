package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.entity.quiz.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
