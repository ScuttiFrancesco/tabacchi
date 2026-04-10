package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.prodotti.grafico.GraficoDto;
import it.tabacchi.prodotti.grafico.GraficoRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimento-magazzino")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "MovimentoMagazzino", description = "API per la gestione dei movimenti di magazzino")
public class MovimentoMagazzinoController {

    private final IMovimentoMagazzinoService movimentoMagazzinoService;

    public MovimentoMagazzinoController(IMovimentoMagazzinoService movimentoMagazzinoService) {
        this.movimentoMagazzinoService = movimentoMagazzinoService;
    }


    @Operation(
            summary = "Inserisci un nuovo movimento di magazzino",
            description = "Crea un nuovo movimento di magazzino con i dettagli specificati"
    )
    @PostMapping
    public ResponseEntity<MovimentoMagazzinoDto> inserisciMovimento(@Valid @RequestBody MovimentoMagazzinoRequest movimento) {
        MovimentoMagazzinoDto nuovoMovimento = movimentoMagazzinoService.create(movimento);
        return new ResponseEntity<>(nuovoMovimento, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Aggiorna un movimento di magazzino esistente",
            description = "Aggiorna i dettagli di un movimento di magazzino esistente specificando l'ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<MovimentoMagazzinoDto> aggiornaMovimento(@Valid @RequestBody MovimentoMagazzinoRequest movimento,
                                                                   @Parameter(description = "ID del movimento di magazzino da aggiornare", required = true) @PathVariable Long id) {
        MovimentoMagazzinoDto movimentoAggiornato = movimentoMagazzinoService.update(movimento, id);
        return new ResponseEntity<>(movimentoAggiornato, HttpStatus.OK);
    }

   /* @Operation(
            summary = "Elimina un movimento di magazzino",
            description = "Elimina un movimento di magazzino specificando l'ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaMovimento(
            @Parameter(description = "ID del movimento di magazzino da eliminare", required = true) @PathVariable Long id) {
        movimentoMagazzinoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @Operation(
            summary = "Recupera un movimento di magazzino per ID",
            description = "Recupera i dettagli di un movimento di magazzino specificando l'ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovimentoMagazzinoDto> getMovimentoById(
            @Parameter(description = "ID del movimento di magazzino", required = true) @PathVariable Long id) {
        MovimentoMagazzinoDto movimento = movimentoMagazzinoService.getById(id);
        return new ResponseEntity<>(movimento, HttpStatus.OK);
    }

    @Operation(
            summary = "Cerca movimenti di magazzino",
            description = "Cerca movimenti di magazzino con filtri e paginazione"
    )
    @GetMapping
    public ResponseEntity<PaginatedResponse<List<MovimentoMagazzinoList>>> searchMovimenti(
            @RequestParam(required = false) LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        MovimentoMagazzinoFilter filter = new MovimentoMagazzinoFilter(data);
        PaginationInfoRequest paginationInfoRequest = new PaginationInfoRequest(page, size, sortBy, Sort.Direction.fromString(sortDirection));

        PaginatedResponse<List<MovimentoMagazzinoList>> response = movimentoMagazzinoService.search(filter, paginationInfoRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
