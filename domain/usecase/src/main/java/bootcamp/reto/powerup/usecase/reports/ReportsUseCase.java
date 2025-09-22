package bootcamp.reto.powerup.usecase.reports;

import bootcamp.reto.powerup.model.reports.Reports;
import bootcamp.reto.powerup.model.reports.gateways.ReportsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReportsUseCase {
    private final ReportsRepository repository;

    public Mono<Reports> finReportById(String id) {
        return repository.findById(id);
    }

    public Mono<Reports> updateReport(Reports reports) {
        return repository.updateReport(reports);
    }
}
