package it.tabacchi.prodotti.movimentomagazzino;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movimento-magazzino")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Movimento Magazzino", description = "API per la gestione dei movimenti di magazzino")
public class MovimentoMagazzinoController {

    private final IMovimentoMagazzinoService movimentoMagazzinoService;

    public MovimentoMagazzinoController(IMovimentoMagazzinoService movimentoMagazzinoService) {
        this.movimentoMagazzinoService = movimentoMagazzinoService;
    }

    @Operation(
        summary = "Ottieni grafico dei movimenti di magazzino",
        description = "Recupera i dati per il grafico dei movimenti di magazzino"
    )
    @GetMapping("/dati-grafico")
    public ResponseEntity<GraficoDto> getDatiGrafico(
        @Parameter(description = "Richiesta per il grafico", required = true)
        @Valid @RequestBody GraficoRequest request
    ) {
        GraficoDto response = movimentoMagazzinoService.getDatiGrafico(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
