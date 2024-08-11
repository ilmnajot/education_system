package uz.ilmnajot.school.model.response;

import jakarta.persistence.*;
import lombok.Data;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.entity.quiz.AnswerAttempt;
import uz.ilmnajot.school.entity.quiz.Test;
import uz.ilmnajot.school.enums.TestAttemptStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TestAttemptResponse {

    private Long id;

    private User user;

    private Test test;

    private LocalDateTime startedTime;

    private LocalDateTime  endedTime;

    private List<AnswerAttempt> answerAttempts;

    private double score;

    private TestAttemptStatus testAttemptStatus;

}
