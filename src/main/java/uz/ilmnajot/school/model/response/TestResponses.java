package uz.ilmnajot.school.model.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.entity.test.Test;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.List;

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
