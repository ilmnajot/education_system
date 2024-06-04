package uz.ilmnajot.school.model.response;

import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private  String email;

    private String phoneNumber;

    private Position position;

    private SchoolName schoolName;

    private Role role;

    private Gender gender;

    //

//    private String password;
//
//    private String rePassword;
}
