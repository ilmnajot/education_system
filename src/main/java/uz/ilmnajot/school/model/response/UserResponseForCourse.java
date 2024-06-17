package uz.ilmnajot.school.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.Position;
import uz.ilmnajot.school.enums.SchoolName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseForCourse {

    private Long id;

    private String firstName;

    private String lastName;

    private  String email;

    public UserResponseForCourse toDto(User user){
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        return this;
    }

}
