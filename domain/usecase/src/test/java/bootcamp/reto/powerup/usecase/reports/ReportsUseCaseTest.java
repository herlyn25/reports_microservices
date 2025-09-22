package bootcamp.reto.powerup.usecase.reports;



import bootcamp.reto.powerup.model.reports.Reports;
import bootcamp.reto.powerup.model.reports.gateways.ReportsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class ReportsUseCaseTest {

        @Mock
        private ReportsRepository repository;

        @InjectMocks
        private ReportsUseCase reportsUseCase; // Asumiendo que esta es la clase que contiene los métodos

        private Reports testReport;

        @BeforeEach
        void setUp() {
            testReport = new Reports();
            testReport.setIdKey("test-id");
            testReport.setAmountAcum(new BigDecimal(0));
            testReport.setCountApps(5);

        }

        @Test
        void finReportById_WhenReportExists_ShouldReturnReport() {
            // Given
            String reportId = "test-id";
            when(repository.findById(reportId)).thenReturn(Mono.just(testReport));

            // When
            Mono<Reports> result = reportsUseCase.finReportById(reportId);

            // Then
            StepVerifier.create(result)
                    .expectNext(testReport)
                    .verifyComplete();

            verify(repository, times(1)).findById(reportId);
        }

        @Test
        void finReportById_WhenReportDoesNotExist_ShouldReturnEmptyMono() {
            // Given
            String reportId = "non-existent-id";
            when(repository.findById(reportId)).thenReturn(Mono.empty());

            // When
            Mono<Reports> result = reportsUseCase.finReportById(reportId);

            // Then
            StepVerifier.create(result)
                    .expectComplete()
                    .verify();

            verify(repository, times(1)).findById(reportId);
        }

        @Test
        void finReportById_WhenRepositoryThrowsError_ShouldPropagateError() {
            // Given
            String reportId = "test-id";
            RuntimeException expectedException = new RuntimeException("Database error");
            when(repository.findById(reportId)).thenReturn(Mono.error(expectedException));

            // When
            Mono<Reports> result = reportsUseCase.finReportById(reportId);

            // Then
            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();

            verify(repository, times(1)).findById(reportId);
        }

        @Test
        void finReportById_WhenIdIsNull_ShouldCallRepositoryWithNull() {
            // Given
            String reportId = null;
            when(repository.findById(reportId)).thenReturn(Mono.empty());

            // When
            Mono<Reports> result = reportsUseCase.finReportById(reportId);

            // Then
            StepVerifier.create(result)
                    .expectComplete()
                    .verify();

            verify(repository, times(1)).findById(reportId);
        }

        @Test
        void updateReport_WhenReportIsValid_ShouldReturnUpdatedReport() {
            // Given
            Reports updatedReport = new Reports();
            updatedReport.setIdKey("test-id");
            updatedReport.setAmountAcum(new BigDecimal(45000));
            updatedReport.setCountApps(3);

            when(repository.updateReport(testReport)).thenReturn(Mono.just(updatedReport));

            // When
            Mono<Reports> result = reportsUseCase.updateReport(testReport);

            // Then
            StepVerifier.create(result)
                    .expectNext(updatedReport)
                    .verifyComplete();

            verify(repository, times(1)).updateReport(testReport);
        }

        @Test
        void updateReport_WhenRepositoryThrowsError_ShouldPropagateError() {
            // Given
            RuntimeException expectedException = new RuntimeException("Update failed");
            when(repository.updateReport(testReport)).thenReturn(Mono.error(expectedException));

            // When
            Mono<Reports> result = reportsUseCase.updateReport(testReport);

            // Then
            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();

            verify(repository, times(1)).updateReport(testReport);
        }

        @Test
        void updateReport_WhenReportIsNull_ShouldCallRepositoryWithNull() {
            // Given
            Reports nullReport = null;
            when(repository.updateReport(nullReport)).thenReturn(Mono.empty());

            // When
            Mono<Reports> result = reportsUseCase.updateReport(nullReport);

            // Then
            StepVerifier.create(result)
                    .expectComplete()
                    .verify();

            verify(repository, times(1)).updateReport(nullReport);
        }

        @Test
        void updateReport_WhenUpdateReturnsEmpty_ShouldReturnEmptyMono() {
            // Given
            when(repository.updateReport(testReport)).thenReturn(Mono.empty());

            // When
            Mono<Reports> result = reportsUseCase.updateReport(testReport);

            // Then
            StepVerifier.create(result)
                    .expectComplete()
                    .verify();

            verify(repository, times(1)).updateReport(testReport);
        }
}
