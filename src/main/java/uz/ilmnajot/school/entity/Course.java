package uz.ilmnajot.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course extends BaseEntity {


    @NotBlank(message = "Name is mandatory")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private String instructor;

    private String imageUrl;

    private double durationInHours;

    @ManyToMany
    private Set<User> users;
}
