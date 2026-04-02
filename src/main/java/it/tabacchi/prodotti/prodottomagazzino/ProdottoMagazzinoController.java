package it.tabacchi.prodotti.prodottomagazzino;

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
@RequestMapping("/api/prodotto-magazzino")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "ProdottoMagazzino", description = "API per la gestione amministrativa delle scorte dei prodotti in magazzino")
public class ProdottoMagazzinoController {

    private final IProdottoMagazzinoService prodottoMagazzinoService;

    public ProdottoMagazzinoController(IProdottoMagazzinoService prodottoMagazzinoService) {
        this.prodottoMagazzinoService = prodottoMagazzinoService;
    }

    @Operation(
            summary = "Aggiorna la scorta di un prodotto in magazzino",
            description = "Aggiorna la scorta attuale di un prodotto in magazzino in base al tipo di movimento (RIFORNIMENTO o VENDITA) e alla quantità specificata."
    )
    @PostMapping
    public ResponseEntity<ProdottoMagazzinoDto> inserisciProdottoMagazzino(@Valid @RequestBody ProdottoMagazzinoRequest request) {
        ProdottoMagazzinoDto prodottoMagazzino = prodottoMagazzinoService.create(request);
        return ResponseEntity.ok(prodottoMagazzino);
    }

    @Operation(
            summary = "Aggiorna la scorta di un prodotto  esistente",
            description = "Aggiorna la scorta di un prodotto esistente specificando l'ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdottoMagazzinoDto> aggiornaProdottoMagazzino(@Valid @RequestBody ProdottoMagazzinoRequest request,
                                                        @Parameter(description = "ID del prodotto di magazzino da aggiornare", required = true) @PathVariable Long id) {
        ProdottoMagazzinoDto prodottoAggiornato = prodottoMagazzinoService.update(request, id);
        return new ResponseEntity<>(prodottoAggiornato, HttpStatus.OK);
    }

    @Operation(
            summary = "Recupera una scorta prodotto per ID",
            description = "Recupera i dettaglidi una scorta di un prodotto specificando l'ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdottoMagazzinoDto> getProdottoById(
            @Parameter(description = "ID del prodotto di magazzino", required = true) @PathVariable Long id) {
        ProdottoMagazzinoDto prodotto = prodottoMagazzinoService.getById(id);
        return new ResponseEntity<>(prodotto, HttpStatus.OK);
    }

    @Operation(
            summary = "Cerca la lista delle scorte dei prodotti con filtri e paginazione",
            description = "Cerca le scorte dei prodotti applicando filtri opzionali come categoria, con supporto per paginazione e ordinamento"
    )
    @GetMapping
    public ResponseEntity<PaginatedResponse<List<ProdottoMagazzinoList>>> searchMovimenti(
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        PaginationInfoRequest paginationInfoRequest = new PaginationInfoRequest(page, size, sortBy, Sort.Direction.fromString(sortDirection));
        PaginatedResponse<List<ProdottoMagazzinoList>> response = null;
        if (categoria != null) {
            response = prodottoMagazzinoService.getAllByCategoria(categoria, paginationInfoRequest);
        }else{
            response = prodottoMagazzinoService.getAll(paginationInfoRequest);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
