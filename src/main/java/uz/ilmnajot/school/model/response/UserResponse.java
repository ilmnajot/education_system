package uz.ilmnajot.school.model.response;

import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String username;

    private String phoneNumber;

    private RoleName roleName;

    private Gender gender;
//
//    private String password;
//
//    private String rePassword;
}
