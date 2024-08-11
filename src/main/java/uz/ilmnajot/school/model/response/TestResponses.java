package uz.ilmnajot.school.model.response;

import lombok.Data;
import uz.ilmnajot.school.entity.quiz.Test;

@Data
public class TestResponses {
    private Long id;

    private String name;

    private String description;



    public TestResponses toTestResponse(Test test) {
        TestResponses response = new TestResponses();
        response.setId(test.getId());
        response.setName(test.getName());
        response.setDescription(test.getDescription());
        return response;
    }

}
