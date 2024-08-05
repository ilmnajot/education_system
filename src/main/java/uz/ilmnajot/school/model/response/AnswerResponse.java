package uz.ilmnajot.school.model.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Question;

@Data
public class AnswerResponse {

    private Long id;

    private String answerText;

    private boolean isCorrect;

}
