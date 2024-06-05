package uz.ilmnajot.school.model.response;

import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.model.request.UserRequest;

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

    private Long roleId;

    private Gender gender;

    public  UserResponse userToDto(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setPosition(user.getPosition());
        response.setSchoolName(user.getSchoolName());
        response.setRoleId(user.getRoleId());
        response.setGender(user.getGender());
        return response;
    }
}
