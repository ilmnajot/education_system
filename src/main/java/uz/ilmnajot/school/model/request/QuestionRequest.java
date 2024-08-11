package uz.ilmnajot.school.model.request;

import jakarta.persistence.*;
import lombok.Data;
import uz.ilmnajot.school.entity.quiz.Option;
import uz.ilmnajot.school.entity.quiz.Test;
import uz.ilmnajot.school.enums.DifficultyLevel;
import uz.ilmnajot.school.enums.Level;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionRequest {

    private Long id;

    private String text;

    private double mark;

    private DifficultyLevel difficultyLevel;

    private Level level;

    private QuestionType questionType;

    private List<Option> options ;

}
