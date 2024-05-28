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

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<Authority> authorities;

    public static Role of(String name){
        Role role = new Role();
        role.setName(name);
        return role;
    }
 }
