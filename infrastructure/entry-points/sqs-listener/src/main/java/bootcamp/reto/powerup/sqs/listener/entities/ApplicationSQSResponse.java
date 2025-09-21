package bootcamp.reto.powerup.sqs.listener.entities;

import com.google.gson.annotations.SerializedName;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ApplicationSQSResponse {
    @SerializedName("amount")
    private BigDecimal amount;
}
