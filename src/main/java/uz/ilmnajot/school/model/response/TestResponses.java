package uz.ilmnajot.school.model.response;

import lombok.Data;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.enums.QuestionType;

@Data
public class TestResponses {

    private Long id;

    private String text;

    private String mark;

    private QuestionType questionType;

//    private Test test;

//    private List<Answer> answers;

    public TestResponses toTestResponse(Question question) {
        TestResponses response = new TestResponses();
        response.setId(question.getId());
        response.setMark(question.getMark());
        response.setQuestionType(question.getQuestionType());
        return response;
    }

}
