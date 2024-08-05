package uz.ilmnajot.school.service.impl;

import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.test.Question;
import uz.ilmnajot.school.exception.BaseException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.response.QuestionResponse;
import uz.ilmnajot.school.repository.QuestionRepository;
import uz.ilmnajot.school.service.QuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public ApiResponse getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()){
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
}
