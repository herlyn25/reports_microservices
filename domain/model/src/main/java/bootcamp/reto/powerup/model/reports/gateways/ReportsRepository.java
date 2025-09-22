package bootcamp.reto.powerup.model.reports.gateways;

import bootcamp.reto.powerup.model.reports.Reports;
import reactor.core.publisher.Mono;

public interface ReportsRepository {
    Mono<Reports> findById(String id);
    Mono<Reports> updateReport(Reports reports);
}
