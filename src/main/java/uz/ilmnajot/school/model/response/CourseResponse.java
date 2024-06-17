package uz.ilmnajot.school.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.entity.Course;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private Long id;

    private String name;

    private String description;

    private String instructor;

    private String imageUrl;

    private double durationInHours;

    public CourseResponse toDto(Course course) {
        this.setId(course.getId());
        this.setName(course.getName());
        this.setDescription(course.getDescription());
        this.setInstructor(course.getInstructor());
        this.setImageUrl(course.getImageUrl());
        this.setDurationInHours(course.getDurationInHours());
        return this;
    }

}
