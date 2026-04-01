package it.tabacchi.prodotti.prodotto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tabacchi.enums.Categoria;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prodotto")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Prodotto", description = "API per la gestione amministrativa dei prodotti")
public class ProdottoController {
    
    private final IProdottoService prodottoService;
            
    public ProdottoController(IProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }

    @Operation(
            summary = "Inserisci un nuovo prodotto",
            description = "Crea un nuovo prodotto con i dettagli specificati"
    )
    @PostMapping
    public ResponseEntity<ProdottoDto> inserisciProdotto(@Valid @RequestBody ProdottoDto prodotto) {
        ProdottoDto nuovoProdotto = prodottoService.create(prodotto);
        return new ResponseEntity<>(nuovoProdotto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Aggiorna un prodotto  esistente",
            description = "Aggiorna i dettagli di un prodotto esistente specificando l'ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdottoDto> aggiornaProdotto(@Valid @RequestBody ProdottoDto prodotto,
                                                                   @Parameter(description = "ID del prodotto di magazzino da aggiornare", required = true) @PathVariable Long id) {
        ProdottoDto prodottoAggiornato = prodottoService.update(prodotto);
        return new ResponseEntity<>(prodottoAggiornato, HttpStatus.OK);
    }

    @Operation(
            summary = "Elimina un prodotto",
            description = "Elimina un prodotto specificando l'ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaProdotto(
            @Parameter(description = "ID del prodotto di magazzino da eliminare", required = true) @PathVariable Long id) {
        prodottoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Recupera un prodotto per ID",
            description = "Recupera i dettagli di un prodotto specificando l'ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdottoDto> getProdottoById(
            @Parameter(description = "ID del prodotto di magazzino", required = true) @PathVariable Long id) {
        ProdottoDto prodotto = prodottoService.getById(id);
        return new ResponseEntity<>(prodotto, HttpStatus.OK);
    }

    @Operation(
            summary = "Cerca la lista dei prodotti con filtri e paginazione",
            description = "Cerca i prodotti applicando filtri opzionali come categoria, con supporto per paginazione e ordinamento"
    )
    @GetMapping
    public ResponseEntity<PaginatedResponse<List<ProdottoList>>> searchMovimenti(
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        ProdottoFilter filter = new ProdottoFilter(categoria);
        PaginationInfoRequest paginationInfoRequest = new PaginationInfoRequest(page, size, sortBy, Sort.Direction.fromString(sortDirection));

        PaginatedResponse<List<ProdottoList>> response = prodottoService.search(filter, paginationInfoRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
