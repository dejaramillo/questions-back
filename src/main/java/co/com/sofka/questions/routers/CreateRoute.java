package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecase.AddAnswerUseCase;
import co.com.sofka.questions.usecase.CreateUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;


@Configuration
public class CreateRoute {

    @Bean
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase){
        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)), serverRequest ->
                        serverRequest.bodyToMono(QuestionDTO.class)
                                .flatMap(questionDTO -> createUseCase.apply(questionDTO).flatMap(result -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(result)))
        );
    }

}
