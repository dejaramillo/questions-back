package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.usecase.GetAllQuestionsUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllQuestionsUseCaseTest {

    @MockBean
    QuestionRepository questionRepository;

    @SpyBean
    GetAllQuestionsUseCase getAllQuestionsUseCase;

    @Test
    void getAllTest() {
        var question = new Question();
        question.setId("01");
        question.setUserId("u01");
        question.setType("test");
        question.setCategory("test");
        question.setQuestion("test?");
        Mockito.when(questionRepository.findAll()).thenReturn(Flux.just(question));
        var resultQuestionDTO = getAllQuestionsUseCase.get().collectList().block();
        Assertions.assertEquals(resultQuestionDTO.get(0).getId(), question.getId());
        Assertions.assertEquals(resultQuestionDTO.get(0).getQuestion(), question.getQuestion());
    }



}