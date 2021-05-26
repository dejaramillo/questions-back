package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetUseCase implements Function<String, Mono<QuestionDTO>> {



    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;

    public GetUseCase(QuestionRepository questionRepository, AnswerRepository answerRepository, MapperUtils mapperUtils) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<QuestionDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return questionRepository.findById(id)
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(questionsWithListAnswers());
    }

    private Function<QuestionDTO, Mono<QuestionDTO>> questionsWithListAnswers(){
        return questionDTO ->
                Mono.just(questionDTO).zipWith(
                        answerRepository.findAllByQuestionId(questionDTO.getId())
                        .map(mapperUtils.mapEntityToAnswer())
                        .collectList(),
                        (question,answers) ->{
                            question.setAnswers(answers);
                            return question;
                        }
                );
    }
}
