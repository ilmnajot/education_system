package uz.ilmnajot.school.model.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.entity.test.Question;

import java.util.List;

@Data
public class TestRequest {


    private String name;

    private String description;

}
