package uz.ilmnajot.school.controller;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.QuestionRequest;
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

    @GetMapping("/getTest/{testId}")
    public HttpEntity<ApiResponse> getQuestion(@PathVariable(name = "testId") Long testId,
                                               @RequestParam(name = "questionId") Long questionId) {
        ApiResponse apiResponse = questionService.getQuestionFromTest(testId, questionId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PutMapping("/updateTest/{questionId}")
    public HttpEntity<ApiResponse> updateTest(@PathVariable(name = "questionId") Long questionId,
                                              @RequestParam(name = "testId") Long testId,
                                              @RequestBody QuestionRequest request) {
        ApiResponse apiResponse = questionService.updateQuestion(testId,questionId, request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/deleteQuestion/{testId}")
    public HttpEntity<ApiResponse> deleteQuestion(@PathVariable(name = "testId") Long testId,
                                              @RequestParam(name = "questionId") Long questionId) {
        ApiResponse apiResponse = questionService.deleteQuestion(testId, questionId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
