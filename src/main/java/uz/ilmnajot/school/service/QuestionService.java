package uz.ilmnajot.school.service;

import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.QuestionRequest;

public interface QuestionService {
    ApiResponse getAllQuestions();

    ApiResponse deleteQuestion(Long testId, Long questionId);

    ApiResponse updateQuestion(Long testId, Long questionId, QuestionRequest request);

    ApiResponse getQuestionFromTest(Long testId, Long questionId);
}
