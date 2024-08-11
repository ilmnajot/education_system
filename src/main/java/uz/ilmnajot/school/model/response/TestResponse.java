package uz.ilmnajot.school.model.response;

import lombok.Data;
import uz.ilmnajot.school.entity.quiz.Question;
import uz.ilmnajot.school.entity.quiz.Test;

import java.util.List;

@Data
public class TestResponse {

    private Long id;

    private String name;

    private String description;

    private List<Question> questions;

    public TestResponse toTestResponse(Test test) {
        TestResponse response = new TestResponse();
        response.setId(test.getId());
        response.setName(test.getName());
        response.setDescription(test.getDescription());
        response.setQuestions(test.getQuestions());
        return response;
    }

}
