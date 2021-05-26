package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {

    private MapperUtils mapperUtils;
    private AnswerRepository answerRepository;
    private GetUseCase getUseCase;

    public AddAnswerUseCase(MapperUtils mapperUtils, AnswerRepository answerRepository, QuestionRepository questionRepository, GetUseCase getUseCase) {
        this.mapperUtils = mapperUtils;
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
    }

    @Override
    public Mono<QuestionDTO> apply(@Valid AnswerDTO answerDTO) {
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO))
                .map(answer -> {
                question.getAnswers().add(answerDTO);
                return question;
                }));
                }

               /* answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO))
                .flatMap(answer -> questionRepository.findById(answer.getQuestionId()))
                .map(question -> {
                   var questionDTO =  mapperUtils.mapEntityToQuestion().apply(question);
                    System.out.println(questionDTO.getCategory());
                   var answersList = answerRepository.findAllByQuestionId(answerDTO.getQuestionId())
                           .map(answer -> mapperUtils.mapEntityToAnswer().apply(answer))
                           .collectList().block();
                   questionDTO.setAnswers(answersList);
                    return questionDTO;

                });
*/
}