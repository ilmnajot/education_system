package uz.ilmnajot.school.service;

import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.enums.QuestionType;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.TestRequest;

import java.util.List;

public interface TestService {
    ApiResponse createTest(String name, String description);

    ApiResponse addQuestionsToTest(Long testId, String text, QuestionType questionType, String mark,List<Answer> answers);

    ApiResponse startTest(Long testId, Long userId);

    ApiResponse submitAnswer(Long attemptId, Long questionId, Long answerId);

    ApiResponse completeTest(Long attemptId);

    ApiResponse getTest(Long testId);

    ApiResponse findAllTests();

    ApiResponse updateTest(Long testId, TestRequest request);

    ApiResponse deleteTest(Long testId);
}
