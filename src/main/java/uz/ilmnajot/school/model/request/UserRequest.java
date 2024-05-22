package uz.ilmnajot.school.model.request;

import lombok.Data;

@Data
public class UserRequest {

    private String fullName;

    private String username;

    private String phoneNumber;

    private String password;

    private String rePassword;

//    private RoleName roleName;

}


