package uz.ilmnajot.school.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.Authority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<Authority> authorities;


 }
