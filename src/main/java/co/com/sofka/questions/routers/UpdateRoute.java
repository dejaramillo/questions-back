package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecase.UpdateUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UpdateRoute {

    @Bean
    public RouterFunction<ServerResponse> updateQuestions(UpdateUseCase updateUseCase){
        return route(
                PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                serverRequest -> serverRequest.bodyToMono(QuestionDTO.class)
                .flatMap(questionDTO -> updateUseCase.apply(questionDTO).flatMap(result -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result)))

        );
    }

}
