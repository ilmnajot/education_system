package uz.ilmnajot.school.model.request;

import jakarta.persistence.*;
import lombok.Data;
import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.entity.test.Test;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.List;

@Data
public class QuestionRequest {

    private Long id;

    private String text;

    private String mark;

    private QuestionType questionType;

}
