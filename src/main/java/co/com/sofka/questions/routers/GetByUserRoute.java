package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecase.GetByUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GetByUserRoute {

    @Bean
    public RouterFunction<ServerResponse> getAllByUser(GetByUserUseCase getByUserUseCase){
        return route(
                GET("/user/{userId}").and(accept(MediaType.APPLICATION_JSON)),
                serverRequest -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getByUserUseCase.apply(serverRequest.pathVariable("userId")), QuestionDTO.class))
        );
    }
}
