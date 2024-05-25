package uz.ilmnajot.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.RoleName;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private String name;



}
