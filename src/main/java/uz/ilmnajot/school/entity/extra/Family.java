package uz.ilmnajot.school.entity.extra;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.FamilyStatus;
import uz.ilmnajot.school.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Family extends BaseEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.mm.yyyy")
    private LocalDateTime birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private byte[] image;

    @Enumerated(EnumType.STRING)
    private FamilyStatus familyStatus;

}
