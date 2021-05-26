package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Validated
public class GetAllQuestionsUseCase implements Supplier<Flux<QuestionDTO>> {

    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;
    private final AnswerRepository answerRepository;

    public GetAllQuestionsUseCase(QuestionRepository questionRepository, MapperUtils mapperUtils, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
        this.answerRepository = answerRepository;
    }

    @Override
    public Flux<QuestionDTO> get() {
        return questionRepository.findAll()
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(allQuestionsWithListAnswers());
    }

    private Function<QuestionDTO, Mono<QuestionDTO>> allQuestionsWithListAnswers(){
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
