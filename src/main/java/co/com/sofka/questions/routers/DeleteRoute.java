package co.com.sofka.questions.routers;

import co.com.sofka.questions.usecase.DeleteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DeleteRoute {

    @Bean
    public RouterFunction<ServerResponse> deleteQuestions(DeleteUseCase deleteUseCase){
        return route(
                DELETE("/del/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        serverRequest -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(serverRequest.pathVariable("id")), Void.class))
        );
    }

}
