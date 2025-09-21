package bootcamp.reto.powerup.sqs.listener;

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
import java.time.Duration;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private Integer elements_count=0;
    private BigDecimal amount_sum=new BigDecimal("0");
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public Mono<Void> apply(Message message) {
        elements_count++;
        return Mono.fromCallable(()-> {
            String messageBody = message.body();
            log.info("Message llega a listener: {}", messageBody);
            JsonObject jsonObject = new JsonParser().parse(messageBody).getAsJsonObject();
            BigDecimal amount = gson.fromJson(jsonObject.get("amount"), BigDecimal.class);
            amount_sum = amount.add(amount);
            return Mono.just(amount);
        }).flatMap(amount-> {
            System.out.println(amount);
            return Mono.just(amount);
        }).timeout(Duration.ofSeconds(30))
                .doOnError(ex-> log.error("error al procesar el mensaje {} : {}",message.messageId(), ex.getMessage())).then();

    }
}
