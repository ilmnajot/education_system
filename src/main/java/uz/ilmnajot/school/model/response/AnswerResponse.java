package uz.ilmnajot.school.model.response;

import lombok.Data;

@Data
public class AnswerResponse {

    private Long id;

    private String answerText;

    private boolean isCorrect;

}
