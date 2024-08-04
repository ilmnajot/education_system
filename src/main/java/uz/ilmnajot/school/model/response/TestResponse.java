package uz.ilmnajot.school.model.response;

import jakarta.persistence.*;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.entity.test.Test;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.List;

@Data
public class TestResponse {

    private Long id;

    private String text;

    private String mark;

    private QuestionType questionType;

//    private Test test;

//    private List<Answer> answers;

    public TestResponse toTestResponse(Question question) {
        TestResponse response = new TestResponse();
        response.setId(question.getId());
        response.setMark(question.getMark());
        response.setQuestionType(question.getQuestionType());
        return response;
    }

}
