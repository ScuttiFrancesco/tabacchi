package it.tabacchi.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
        @Parameter(description = "ID dell'utente da aggiornare", required = true)
        @PathVariable Long id,
        @Parameter(description = "Nuovi dati dell'utente", required = true)
        @Valid @RequestBody UserCreationRequest request
    ) {
        UserDto updatedUser = userService.update(id, request);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @Operation(
        summary = "Ottieni utente per ID",
        description = "Recupera i dettagli di un utente tramite il suo ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
        @Parameter(description = "ID dell'utente da recuperare", required = true)
        @PathVariable Long id
    ) {
        UserDto user = userService.getById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
        summary = "Ottieni utente per email",
        description = "Recupera i dettagli di un utente tramite il suo indirizzo email"
    )
    @GetMapping("/email={email}")
    public ResponseEntity<UserDto> getUserByEmail(
        @Parameter(description = "Email dell'utente da recuperare", required = true)
        @PathVariable String email
    ) {
        UserDto user = userService.getByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
