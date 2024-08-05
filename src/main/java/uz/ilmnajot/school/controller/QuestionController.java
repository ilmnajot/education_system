package uz.ilmnajot.school.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    public HttpEntity<ApiResponse> getAllQuestions() {
        ApiResponse apiResponse = questionService.getAllQuestions();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.notFound().build();
    }
}
