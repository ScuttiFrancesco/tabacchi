package it.tabacchi.prodotti.prezzostorico;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tabacchi.enums.Categoria;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prezzo-storico")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Prezzo Storico", description = "API per la gestione dei prezzi storici dei prodotti")
public class PrezzoStoricoController {

    private final IPrezzoStoricoService prezzoStoricoService;

    public PrezzoStoricoController(IPrezzoStoricoService prezzoStoricoService) {
        this.prezzoStoricoService = prezzoStoricoService;
    }

    @Operation(summary = "Inserisci una lista di prezzi storici", description = "Crea un nuovo prezzo storico con i dettagli specificati")
    @PostMapping
    public ResponseEntity<Void> inserisciPrezzoStorico(
        @Valid @RequestBody List<PrezzoStoricoRequest> prezzoStorico,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine
    ) {
        prezzoStoricoService.bulkInsert(prezzoStorico, dataInizio, dataFine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Aggiorna un prezzo storico esistente", description = "Aggiorna i dettagli di un prezzo storico esistente specificando l'ID")
    @PutMapping("/{id}")
    public ResponseEntity<PrezzoStoricoDto> aggiornaPrezzoStorico(@Valid @RequestBody PrezzoStoricoRequest prezzoStorico,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
            @Parameter(description = "ID del prezzo storico da aggiornare", required = true) @PathVariable Long id) {


        PrezzoStoricoDto prezzoStoricoAggiornato = prezzoStoricoService.update(id, prezzoStorico, dataInizio, dataFine);
        return new ResponseEntity<>(prezzoStoricoAggiornato, HttpStatus.OK);
    }

    @Operation(summary = "Recupera un prezzo storico per ID", description = "Recupera i dettagli di un prezzo storico specificando l'ID")
    @GetMapping("/{id}")
    public ResponseEntity<PrezzoStoricoDto> getPrezzoStoricoById(
            @Parameter(description = "ID del prezzo storico", required = true) @PathVariable Long id) {
        PrezzoStoricoDto prezzoStorico = prezzoStoricoService.getPrezzoStoricoById(id);
        return new ResponseEntity<>(prezzoStorico, HttpStatus.OK);
    }

    @Operation(summary = "Cerca la lista dei prezzi storici con filtri e paginazione", description = "Cerca i prezzi storici applicando filtri opzionali come categoria, con supporto per paginazione e ordinamento")
    @GetMapping
    public ResponseEntity<PaginatedResponse<List<PrezzoStoricoDto>>> searchPrezziStorici(
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) Boolean isAttivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        PrezzoStoricoFilter filter = new PrezzoStoricoFilter(categoria, isAttivo);
        PaginationInfoRequest paginationInfoRequest = new PaginationInfoRequest(page, size, sortBy,
                Sort.Direction.fromString(sortDirection));

        PaginatedResponse<List<PrezzoStoricoDto>> response = prezzoStoricoService.search(paginationInfoRequest, filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
