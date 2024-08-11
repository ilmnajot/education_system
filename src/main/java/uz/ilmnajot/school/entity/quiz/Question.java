package uz.ilmnajot.school.entity.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.enums.DifficultyLevel;
import uz.ilmnajot.school.enums.Level;
import uz.ilmnajot.school.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "QUESTION")
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private double mark;

    @Enumerated(value = EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    @Enumerated(value = EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonIgnore
    private Test test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();


    public Question(Long id, String question, double mark, DifficultyLevel difficultyLevel, Level level, QuestionType questionType, List<Option> options) {
    this.id = id;
    this.text = question;
    this.mark = mark;
    this.difficultyLevel = difficultyLevel;
    this.level = level;
    this.questionType = questionType;
    this.options = options;
    }

    public Question(String text, double mark, DifficultyLevel difficultyLevel, Level level, QuestionType questionType, Test test, List<Option> options) {
        this.text = text;
        this.mark = mark;
        this.difficultyLevel = difficultyLevel;
        this.level = level;
        this.questionType = questionType;
        this.test = test;
        this.options = options;
    }
}
