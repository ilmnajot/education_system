package uz.ilmnajot.school.model.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.entity.test.Test;
import uz.ilmnajot.school.enums.QuestionType;

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
