package bootcamp.reto.powerup.model.reports;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reports {
    private String idKey;
    private Integer countApps;
    private BigDecimal amountAcum;
}
