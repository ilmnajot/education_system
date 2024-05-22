package uz.ilmnajot.school.model.request;

import lombok.Data;
import uz.ilmnajot.school.enums.Gender;

@Data
public class UserRequest {

    private String fullName;

    private String username;

    private String phoneNumber;

    private String password;

    private String rePassword;

    private Gender gender;

//    private RoleName roleName;

}


