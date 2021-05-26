package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.usecase.AddAnswerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CreateAnswerRoute {

    @Bean
    public RouterFunction<ServerResponse> createAnswers(AddAnswerUseCase addAnswerUseCase){
        return  route(
                POST("/c-answer").and(accept(MediaType.APPLICATION_JSON)), serverRequest ->
                        serverRequest.bodyToMono(AnswerDTO.class)
                                .flatMap(answerDTO -> addAnswerUseCase.apply(answerDTO).flatMap(result -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }
}
