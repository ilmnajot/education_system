package uz.ilmnajot.school.model.request;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Data;
import uz.ilmnajot.school.entity.User;

@Data
public class CourseRequest {

    private String name;

    private String description;

    private String instructor;

    private String imageUrl;

    private double durationInHours;

}
