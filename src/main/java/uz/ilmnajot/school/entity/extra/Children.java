package uz.ilmnajot.school.entity.extra;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.FamilyStatus;
import uz.ilmnajot.school.enums.Gender;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Children extends BaseEntity {

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

    private byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;



}
