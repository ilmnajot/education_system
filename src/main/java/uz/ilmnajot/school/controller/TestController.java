package uz.ilmnajot.school.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.entity.test.Answer;
import uz.ilmnajot.school.enums.QuestionType;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/addTest") // working...
    public HttpEntity<ApiResponse> createTest(
            @RequestParam String name,
            @RequestParam String description) {
        ApiResponse apiResponse = testService.createTest(name, description);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/addQuestion")
    public HttpEntity<ApiResponse> addQuestionToTest(
            @RequestParam(name = "testId") Long testId,
            @RequestParam(name = "text") String text,
            @RequestParam QuestionType questionType,
            @RequestParam(name = "mark") String mark,
            @RequestBody List<Answer> answers) {
        ApiResponse apiResponse = testService.addQuestionsToTest(testId, text, questionType,mark, answers);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/{testId}/start")
    public HttpEntity<ApiResponse> startTest(
            @PathVariable(name = "testId") Long testId,
            @RequestParam(name = "userId") Long userId) {
        ApiResponse apiResponse = testService.startTest(testId, userId);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/attempts/{attemptId}/answer")
    public HttpEntity<ApiResponse> submitTest(
            @PathVariable(name = "attemptId") Long attemptId,
            @RequestParam(name = "questionId") Long questionId,
            @RequestParam(name = "answerId") Long answerId
            ){
        ApiResponse apiResponse = testService.submitAnswer(attemptId, questionId, answerId);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/attempts/{attemptId}/complete")
    public HttpEntity<ApiResponse> completeTest(@PathVariable(name = "attemptId") Long attemptId) {
        ApiResponse apiResponse = testService.completeTest(attemptId);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getTest/{testId}")
    public HttpEntity<ApiResponse> getTest(@PathVariable(name = "testId") Long testId){
        ApiResponse apiResponse = testService.getTest(testId);
        return apiResponse !=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
