package uz.ilmnajot.school.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.entity.quiz.Option;
import uz.ilmnajot.school.enums.QuestionType;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.QuestionRequest;
import uz.ilmnajot.school.model.request.TestRequest;
import uz.ilmnajot.school.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PreAuthorize("hasAuthority('ADD_TEST')")
    @PostMapping("/addTest") // working...
    public HttpEntity<ApiResponse> createTest(
            @RequestParam String name,
            @RequestParam String description) {
        ApiResponse apiResponse = testService.createTest(name, description);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('ADD_QUESTION')")
    @PostMapping("/addQuestion")
    public HttpEntity<ApiResponse> addQuestionToTest(
            @RequestParam(name = "testId") Long testId,
            @RequestBody List<QuestionRequest> requests) {
        ApiResponse apiResponse = testService.addQuestionsToTest(testId, requests);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('START_TEST')")
    @PostMapping("/{testId}/start")
    public HttpEntity<ApiResponse> startTest(
            @PathVariable(name = "testId") Long testId,
            @RequestParam(name = "userId") Long userId) {
        ApiResponse apiResponse = testService.startTest(testId, userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('ATTEMPT_TES')")
    @PostMapping("/attempts/{attemptId}/answer")
    public HttpEntity<ApiResponse> submitTest(
            @PathVariable(name = "attemptId") Long attemptId,
            @RequestParam(name = "questionId") Long questionId,
            @RequestParam(name = "answerId") Long answerId) {
        ApiResponse apiResponse = testService.submitAnswer(attemptId, questionId, answerId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('COMPLETE_TEST')")
    @PostMapping("/attempts/{attemptId}/complete")
    public HttpEntity<ApiResponse> completeTest(@PathVariable(name = "attemptId") Long attemptId) {
        ApiResponse apiResponse = testService.completeTest(attemptId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('GET_TEST')")
    @GetMapping("/getTest/{testId}")
    public HttpEntity<ApiResponse> getTest(@PathVariable(name = "testId") Long testId) {
        ApiResponse apiResponse = testService.getTest(testId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_TEST')")
    @GetMapping("/getAllTests")
    public HttpEntity<ApiResponse> getAllTests() {
        ApiResponse apiResponse = testService.findAllTests();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('UDPATE_TEST')")
    @PutMapping("/updateTest/{testId}")
    public HttpEntity<ApiResponse> updateTest(@PathVariable(name = "testId") Long testId,
                                              @RequestBody TestRequest request) {
        ApiResponse apiResponse = testService.updateTest(testId, request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('DELETE_TEST')")
    @DeleteMapping("/deleteTest/{testId}")
    public HttpEntity<ApiResponse> deleteTest(@PathVariable(name = "testId") Long testId) {
        ApiResponse apiResponse = testService.deleteTest(testId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
