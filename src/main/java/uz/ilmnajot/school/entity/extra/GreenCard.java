package uz.ilmnajot.school.entity.extra;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.Education;
import uz.ilmnajot.school.enums.FamilyStatus;
import uz.ilmnajot.school.enums.Gender;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GreenCard extends BaseEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yy.mm.yyyy")
    private LocalDateTime birthDate;

    @NotNull
    private String phoneNumber;


    @NotNull
    @Enumerated(EnumType.STRING)
    private Education education;

    @NotNull
    private byte[] image;

    @NotNull
    private int numberOfChildren;

    @OneToOne(cascade = CascadeType.ALL)
    private Family family;

    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Children> children;



}
