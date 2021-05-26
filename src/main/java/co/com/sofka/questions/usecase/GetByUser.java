package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class GetByUser implements Function<String, Flux<QuestionDTO>> {

    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public GetByUser(QuestionRepository questionRepository, MapperUtils mapperUtils) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<QuestionDTO> apply(String id) {
        return questionRepository.findByUserId(id)
                .map(mapperUtils.mapEntityToQuestion());
    }
}
