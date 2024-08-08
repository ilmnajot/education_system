package uz.ilmnajot.school.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.entity.test.*;
import uz.ilmnajot.school.enums.QuestionType;
import uz.ilmnajot.school.exception.BaseException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.TestRequest;
import uz.ilmnajot.school.model.response.TestResponse;
import uz.ilmnajot.school.model.response.TestResponses;
import uz.ilmnajot.school.repository.*;
import uz.ilmnajot.school.service.TestService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    private final TestAttemptRepository testAttemptRepository;

    private final AnswerAttemptRepository answerAttemptRepository;


    public TestServiceImpl(TestRepository testRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, TestAttemptRepository testAttemptRepository, AnswerAttemptRepository answerAttemptRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.answerAttemptRepository = answerAttemptRepository;
    }


    @Override
    public ApiResponse createTest(String name, String description) {
        Optional<Test> byName = testRepository.findByName(name);
        if (byName.isPresent()) {
            throw new BaseException("Test already exists", HttpStatus.BAD_REQUEST);
        }
        Test test = Test.builder()
                .name(name)
                .description(description)
                .build();
        Test savedTest = testRepository.save(test);
        return new ApiResponse("success", true, savedTest);
    }

    @Override
    public ApiResponse addQuestionsToTest(
            Long testId,
            String text,
            QuestionType questionType,
            String mark,
            List<Answer> answers) {
        Test test = testRepository.findById(testId).orElseThrow(()
                -> new BaseException("there is no test with id: " + testId, HttpStatus.NOT_FOUND));
        Question question = Question.builder()
                .text(text)
                .mark(mark)
                .questionType(questionType)
                .test(test)
                .answers(answers)
                .build();

        for (Answer answer : answers) {
            answer.setQuestion(question);
            answerRepository.save(answer);
        }
        Question savedQuestion = questionRepository.save(question);
        TestResponses testResponses = new TestResponses().toTestResponse(savedQuestion.getTest());// todo
        return new ApiResponse("success", true, testResponses);
    }

    @Override
    public ApiResponse startTest(Long testId, Long userId) {
        Test test = getTestById(testId);
        User user = getUserById(userId);

        TestAttempt attempt = TestAttempt.builder()
                .user(user)
                .test(test)
                .startedTime(LocalDateTime.now())
                .build();
        TestAttempt savedTest = testAttemptRepository.save(attempt);
        return new ApiResponse("success", true, savedTest);
    }

    @Override
    public ApiResponse submitAnswer(Long attemptId, Long questionId, Long answerId) {
        Question question = getQuestionById(questionId);
        Answer answer = getAnswerById(answerId);
        TestAttempt testAttempt = getAttemptById(attemptId);
        boolean correct = answer.isCorrect();

        AnswerAttempt attempt = AnswerAttempt.builder()
                .testAttempt(testAttempt)
                .question(question)
                .answer(answer)
                .isCorrect(correct)
                .build();

        AnswerAttempt savedAttempt = answerAttemptRepository.save(attempt);
        return new ApiResponse("success", true, savedAttempt);
    }

    @Override
    public ApiResponse completeTest(Long attemptId) {
        TestAttempt testAttempt = getAttemptById(attemptId);
        long countedCorrectAnswers = testAttempt
                .getAnswerAttempts()
                .stream()
                .filter(AnswerAttempt::isCorrect)
                .count();
        int totalQuestions = testAttempt
                .getTest()
                .getQuestions()
                .size();
        double score = (double) countedCorrectAnswers / totalQuestions * 100;
        testAttempt.setScore(score);
        testAttempt.setEndedTime(LocalDateTime.now());

        TestAttempt savedAttempt = testAttemptRepository.save(testAttempt);
        return new ApiResponse("success", true, savedAttempt);
    }

    @Override
    public ApiResponse getTest(Long testId) {
        Test test = getTestById(testId);
        TestResponse testResponse = new TestResponse().toTestResponse(test);
        return new ApiResponse("success", true, testResponse);
    }

    @Override
    public ApiResponse findAllTests() {
        List<Test> tests = testRepository.findAll();
        List<TestResponses> list = tests
                .stream()
                .map(test -> new TestResponses().toTestResponse(test))
                .toList();
        Map<String, Object> data = new HashMap<>();
        data.put("tests", list);
        data.put("currentPage", 1);
        data.put("totalPages", list.size());
        return new ApiResponse("success", true, data);
    }

    @Override
    public ApiResponse updateTest(Long testId, TestRequest request) {
        Test test = getTestById(testId);
        test.setId(testId);
        test.setName(request.getName());
        test.setDescription(request.getDescription());
        Test updatedTest = testRepository.save(test);
        TestResponse testResponse = new TestResponse().toTestResponse(updatedTest);
        return new ApiResponse("success", true, testResponse);
    }

    @Override
    public ApiResponse deleteTest(Long testId) {
        getTestById(testId);
        testRepository.deleteById(testId);
        return new ApiResponse("success", true, "test with id: " + testId + " has been removed");
    }


    private Test getTestById(Long testId) {
        Optional<Test> byId = testRepository.findById(testId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseException("there is no test with id: " + testId, HttpStatus.NOT_FOUND);
    }

    private User getUserById(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseException("there is no user with id: " + userId, HttpStatus.NOT_FOUND);
    }

    private TestAttempt getAttemptById(Long attemptId) {
        Optional<TestAttempt> byId = testAttemptRepository.findById(attemptId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseException("there is no attempt with id: " + attemptId, HttpStatus.NOT_FOUND);
    }

    private Question getQuestionById(Long questionId) {
        Optional<Question> byId = questionRepository.findById(questionId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseException("there is no question with id: " + questionId, HttpStatus.NOT_FOUND);
    }

    private Answer getAnswerById(Long answerId) {
        Optional<Answer> byId = answerRepository.findById(answerId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseException("there is no answer with id: " + answerId, HttpStatus.NOT_FOUND);
    }

}
