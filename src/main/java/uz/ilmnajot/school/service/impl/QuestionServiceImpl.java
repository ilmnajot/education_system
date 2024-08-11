package uz.ilmnajot.school.service.impl;

import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.quiz.Question;
import uz.ilmnajot.school.entity.quiz.Test;
import uz.ilmnajot.school.exception.BaseException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.QuestionRequest;
import uz.ilmnajot.school.model.response.QuestionResponse;
import uz.ilmnajot.school.repository.QuestionRepository;
import uz.ilmnajot.school.repository.TestRepository;
import uz.ilmnajot.school.service.QuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }


    @Override
    public ApiResponse getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new BaseException("no questions found");
        }
        List<QuestionResponse> list = questions
                .stream()
                .map(question -> new QuestionResponse().toQuestionResponse(question))
                .toList();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("questions", list);
        data.put("current page", 1);
        data.put("total", questions.size());
        return new ApiResponse("success", true, data);
    }

    @Override
    public ApiResponse deleteQuestion(Long testId, Long questionId) {
        Question question = getQuestion(questionId);
        Test test = getTest(testId);

        if (!test.getQuestions().contains(question)) {
            throw new BaseException("question does not exist");
        }
        test.getQuestions().remove(question);
        testRepository.save(test);
        return new ApiResponse("success", true, "question has been removed");
    }

    @Override
    public ApiResponse updateQuestion(Long testId, Long questionId, QuestionRequest request) {
        Test test = getTest(testId);
        Question questionToUpdate = test.getQuestions()
                .stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new BaseException("no question found "));
//        questionToUpdate.setId(questionId); // no need to redundant
        questionToUpdate.setText(request.getText());
        questionToUpdate.setMark(request.getMark());
        questionToUpdate.setQuestionType(request.getQuestionType());
        questionRepository.save(questionToUpdate);
//        testRepository.save(test);
        return new ApiResponse("success", true, "question has been updated");
    }

    @Override
    public ApiResponse getQuestionFromTest(Long testId, Long questionId) {
        Test test = getTest(testId);
        Question questionFound = test
                .getQuestions()
                .stream().
                filter(question -> question.getId().equals(questionId))
                .findFirst().orElseThrow(()
                        -> new BaseException("no question found "));
        return new ApiResponse("success", true, questionFound);
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId).orElseThrow(()
                -> new BaseException("there is no test found with id: "));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(()
                -> new BaseException("there is no question found with id: "));
    }
}
