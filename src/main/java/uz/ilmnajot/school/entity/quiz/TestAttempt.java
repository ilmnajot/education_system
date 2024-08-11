package uz.ilmnajot.school.entity.quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.entity.User;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private LocalDateTime  startedTime;

    private LocalDateTime  endedTime;

    @OneToMany
    private List<AnswerAttempt> answerAttempts;

    private double score;
}
