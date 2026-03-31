package it.tabacchi.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tabacchi.pagination.PaginatedResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "User", description = "API per la gestione amministrativa degli utenti")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "Aggiorna un utente esistente",
        description = "Aggiorna i dati di un utente esistente tramite ID"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PaginatedResponse<UserDto>> updateUser(
        @Parameter(description = "ID dell'utente da aggiornare", required = true)
        @PathVariable Long id,
        @Parameter(description = "Nuovi dati dell'utente", required = true)
        @Valid @RequestBody UserCreationRequest request
    ) {
        UserDto updatedUser = userService.update(id, request);
        PaginatedResponse<UserDto> response = new PaginatedResponse<>();
        response.setData(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
        summary = "Ottieni utente per ID",
        description = "Recupera i dettagli di un utente tramite il suo ID"
    )
    @PreAuthorize("hasAnyAuthority('CREAZIONE_UTENTE', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse<UserDto>> getUserById(
        @Parameter(description = "ID dell'utente da recuperare", required = true)
        @PathVariable Long id
    ) {
        UserDto user = userService.getById(id);
        PaginatedResponse<UserDto> response = new PaginatedResponse<>();
        response.setData(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
        summary = "Ottieni utente per email",
        description = "Recupera i dettagli di un utente tramite il suo indirizzo email"
    )
    @PreAuthorize("hasAnyAuthority('CREAZIONE_UTENTE', 'ADMIN')")
    @GetMapping("/email={email}")
    public ResponseEntity<PaginatedResponse<UserDto>> getUserByEmail(
        @Parameter(description = "Email dell'utente da recuperare", required = true)
        @PathVariable String email
    ) {
        UserDto user = userService.getByEmail(email);
        PaginatedResponse<UserDto> response = new PaginatedResponse<>();
        response.setData(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
