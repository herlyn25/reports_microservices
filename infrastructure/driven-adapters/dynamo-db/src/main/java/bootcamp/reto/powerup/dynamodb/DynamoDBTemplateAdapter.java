package bootcamp.reto.powerup.dynamodb;

import bootcamp.reto.powerup.dynamodb.exceptions.ConstantsException;
import bootcamp.reto.powerup.dynamodb.exceptions.NoSourceResourceException;
import bootcamp.reto.powerup.dynamodb.helper.TemplateAdapterOperations;
import bootcamp.reto.powerup.model.reports.Reports;
import bootcamp.reto.powerup.model.reports.gateways.ReportsRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Slf4j
@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Reports, String, ReportsEntity> implements ReportsRepository {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, Reports.class), "reports_crediya");
    }

    public Mono<List<Reports>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<Reports>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression, "secondary_index" /*index is optional if you define in constructor*/);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<Reports> findById(String id) {
        return super.getById(id)
                .switchIfEmpty(Mono.error(new NoSourceResourceException(ConstantsException.NO_FOUND_RESOURCE)));
    }

    @Override
    public Mono<Reports> updateReport(Reports reports) {
            log.info("Entro a guardar: {}", reports.getCountApps());
            log.info("Entro a guardar: {}", reports.getAmountAcum());
            return super.save(reports);
    }
}
