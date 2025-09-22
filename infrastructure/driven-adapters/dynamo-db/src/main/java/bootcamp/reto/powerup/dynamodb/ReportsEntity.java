package bootcamp.reto.powerup.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReportsEntity {
    private String idKey;
    private Integer countApps;
    private BigDecimal amountAcum;

    public ReportsEntity() {
    }

    public ReportsEntity(String idKey, Integer countApps, BigDecimal amountAcum) {
        this.idKey = idKey;
        this.countApps = countApps;
        this.amountAcum = amountAcum;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id_key")
    public String getIdKey() {
        return idKey;
    }
    public void setIdKey(String idKey) { this.idKey = idKey;}

    @DynamoDbAttribute("amount_acum")
    public BigDecimal getAmountAcum() { return amountAcum; }
    public void setAmountAcum(BigDecimal amountAcum) {
        this.amountAcum = amountAcum;
    }

    @DynamoDbAttribute("count_apps")
    public Integer getCountApps() { return countApps; }
    public void setCountApps(Integer countApps) {
        this.countApps = countApps;
    }
}
