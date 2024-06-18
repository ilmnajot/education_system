package uz.ilmnajot.school.entity.extra;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.Country;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Address extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Country birthCountry;

    @NotNull
    private String birthRegion;

    @NotNull
    private String birthCity;

    @NotNull
    private String place;
}
