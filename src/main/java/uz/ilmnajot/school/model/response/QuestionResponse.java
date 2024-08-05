package uz.ilmnajot.school.model.response;

import jakarta.persistence.*;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.entity.test.Test;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.List;

@Data
public class QuestionResponse {

    private Long id;

    private String text;

    private String mark;

    private QuestionType questionType;

    private Long testId;

    private List<Answer> answers;

    public QuestionResponse toQuestionResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setText(question.getText());
        response.setMark(question.getMark());
        response.setQuestionType(question.getQuestionType());
        response.setAnswers(question.getAnswers());
        return response;

    }
}
