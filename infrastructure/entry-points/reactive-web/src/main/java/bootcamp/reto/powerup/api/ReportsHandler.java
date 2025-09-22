package bootcamp.reto.powerup.api;

import bootcamp.reto.powerup.usecase.reports.ReportsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReportsHandler {
private  final ReportsUseCase reportsUseCase;

    public Mono<ServerResponse> listenGetReportById(ServerRequest serverRequest) {
        String id_key = serverRequest.pathVariable("id_key");
        return reportsUseCase.finReportById(id_key).flatMap(report->ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON).bodyValue(report));
    }
}
