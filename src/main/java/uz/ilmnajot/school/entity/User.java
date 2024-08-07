package uz.ilmnajot.school.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.Authority;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
@Setter
@Getter
public class User extends BaseEntity implements UserDetails {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    @Size(min = 8, max = 25, message = "please try to check your email and must be between 8 and 25")
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Position position; //enum

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses;

    @Column(name = "role_id",insertable = false, updatable = false)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = role.getAuthorities();
        return authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .toList();
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
