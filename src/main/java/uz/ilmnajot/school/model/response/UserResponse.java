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
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setPosition(user.getPosition());
        this.setSchoolName(user.getSchoolName());
        this.setRoleId(user.getRoleId());
        this.setGender(user.getGender());
        return this;
    }
    public UserResponse toDto(User user){
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        return this;
    }

}
