package co.com.sofka.questions.usecase;


import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public class CreateUseCase implements SaveQuestions {

    private final MapperUtils mapperUtils;
    private final QuestionRepository questionRepository;

    public CreateUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.mapperUtils = mapperUtils;
        this.questionRepository = questionRepository;
    }

    @Override
    public Mono<String> apply(@Valid QuestionDTO questionDTO) {
        return questionRepository.save(mapperUtils.mapperToQuestion(null).apply(questionDTO))
                .map(Question::getId);
    }
}
