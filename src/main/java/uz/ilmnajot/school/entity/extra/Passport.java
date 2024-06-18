package uz.ilmnajot.school.entity.extra;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PASSPORT")
@Builder
public class Passport extends BaseEntity {

    @NotNull
    private String passportNumber;

    @NotNull
    private String issueDate;

    @NotNull
    private String givenBy;

    @NotNull
    private String address;

}
