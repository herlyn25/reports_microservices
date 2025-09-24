package bootcamp.reto.powerup.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name="Reports" , description = "Reports about approved credit requests")
public class ReportsRouter {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reports/{idKey}",
                    method = RequestMethod.GET,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = ReportsHandler.class,
                    beanMethod = "listenGetReportById",
                    operation = @Operation(
                            operationId = "AppConsummerAuth",
                            summary = "Consultar numero y presupuesto acumulado de las solicitudes aprobadas",
                            tags = { "Reports" },
                            parameters = {
                                    @Parameter(
                                            name="idKey",
                                            description = "Indica la clave del reporte",
                                            example = "Ej. report_credits",
                                            schema = @Schema(type = "String", defaultValue = "")
                                    )
                            },
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos de las solicitudes de prestamo consultadas",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = bootcamp.reto.powerup.model.reports.Reports.class // ← cambia al paquete real de tu DTO si es distinto
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Obtener listado creditos aprobados",
                                            content = @Content(
                                                    schema = @Schema(implementation = bootcamp.reto.powerup.model.reports.Reports.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "No se encontro recuro"),
                                    @ApiResponse(responseCode = "401", description = "No Autorizado indica una falla en la autenticación"),
                                    @ApiResponse(responseCode = "403", description = "No autorizado para recurso aun con token")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/health",
                    method = RequestMethod.GET,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = ReportsHandler.class,
                    beanMethod = "listenHealthCheck",
                    operation = @Operation(
                            operationId = "Health",
                            summary = "Verificar que el microservicio funcione",
                            tags = { "Reports" },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Validar microservicio",
                                            content =@Content(
                                                    schema = @Schema(implementation = bootcamp.reto.powerup.model.reports.Health.class)
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(ReportsHandler handler) {
        return route(GET("/api/v1/reports/{id_key}"), handler::listenGetReportById)
                .andRoute(GET("/health"),handler::listenHealthCheck);
    }
}
