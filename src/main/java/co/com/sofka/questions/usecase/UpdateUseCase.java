package co.com.sofka.questions.usecase;


import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UpdateUseCase implements SaveQuestions {

    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public UpdateUseCase(QuestionRepository questionRepository, MapperUtils mapperUtils) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(QuestionDTO questionDTO) {
        return questionRepository.save(mapperUtils.mapperToQuestion(questionDTO.getId()).apply(questionDTO))
                .map(Question::getId);
    }
}
