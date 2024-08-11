package uz.ilmnajot.school.entity.quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.TestAttemptStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TEST_ATTEMPT")
@Builder
public class TestAttempt {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    private LocalDateTime  startedTime;

    private LocalDateTime  endedTime;

    @OneToMany(mappedBy = "testAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerAttempt> answerAttempts;

    private double score;

    @Enumerated(value = EnumType.STRING)
    private TestAttemptStatus testAttemptStatus;

}
