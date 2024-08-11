package uz.ilmnajot.school.model.response;

import lombok.Data;
import uz.ilmnajot.school.entity.quiz.Question;
import uz.ilmnajot.school.enums.QuestionType;

@Data
public class QuestionResponse {

    private Long id;

    private String text;

    private double mark;

    private QuestionType questionType;

    private Long testId;

//    private List<Answer> answers;

    public QuestionResponse toQuestionResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setText(question.getText());
        response.setMark(question.getMark());
        response.setQuestionType(question.getQuestionType());
//        response.setAnswers(question.getAnswers());
        return response;

    }
}
