package bootcamp.reto.powerup.sqs.listener;

import bootcamp.reto.powerup.model.reports.Reports;
import bootcamp.reto.powerup.usecase.reports.ReportsUseCase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.math.BigDecimal;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ReportsUseCase reportsUseCase;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public Mono<Void> apply(Message message) {
        log.info("Procesando mensaje ID: {}", message.messageId());

        return reportsUseCase.finReportById("report_credits")
                .flatMap(report-> {
                    try {
                        // Convert the message
                        String messageBody = message.body();
                        JsonObject jsonObject = new JsonParser().parse(messageBody).getAsJsonObject();
                        BigDecimal amount = gson.fromJson(jsonObject.get("amount"), BigDecimal.class);

                        // calcular nuevos valores
                        Integer newCount = report.getCountApps() + 1;
                        BigDecimal newAmount = report.getAmountAcum().add(amount);

                        // Actualizar objeto
                        report.setCountApps(newCount);
                        report.setAmountAcum(newAmount);

                        log.info("Listener SQS # items: {}, Acum: {}", newCount, newAmount);
                        return reportsUseCase.updateReport(report);
                    }catch (Exception ex) {
                        log.error("error al procesar el mensaje {} : {}",message.messageId(), ex.getMessage());
                        return Mono.error(ex);
                    }
        }).doOnSuccess(updateRecord-> log.info("Record actualizado satisfactoriamente"))
                .doOnError(ex-> {
                    log.error("error al procesar el mensaje {} : {}",message.messageId(), ex.getMessage(), ex);
                }).then();

    }
}
