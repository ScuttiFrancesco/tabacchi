package it.tabacchi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.mail.MailException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "not_found");
        body.put("title", "Entity Not Found");
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("detail", ex.getMessage());
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Object> handleDuplicateData(DuplicateDataException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "data_conflict");
        body.put("title", "Duplicate Data Attribute");
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("detail", ex.getMessage());
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidRefreshTokenException.class, ExpiredRefreshTokenException.class})
    public ResponseEntity<Object> handleRefreshTokenException(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "authentication_failed");
        body.put("title", "Authentication Failed");
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("detail", ex.getMessage());
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<Object> handleMailException(MailException ex) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = isInvalidRecipientAddress(ex) ? HttpStatus.BAD_REQUEST : HttpStatus.SERVICE_UNAVAILABLE;

        body.put("type", "email_delivery_failed");
        body.put("title", "Email Delivery Failed");
        body.put("status", status.value());
        body.put("detail", isInvalidRecipientAddress(ex)
                ? "Invio email non riuscito: l'indirizzo del destinatario non e' valido."
                : "Invio email non riuscito. Riprova piu' tardi.");
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "internal_server_error");
        body.put("title", "Internal Server Error");
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("detail", ex.getMessage());
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings("null")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        body.put("type", "validation_error");
        body.put("properties", fieldErrors);
        body.put("title", "Validation Failed");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("detail", "La validazione dei dati è fallita. Controlla i campi dell'oggetto inviato. Dettagli nell'oggetto properties.");
        body.put("instance", null);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "access_denied");
        body.put("title", "Access Denied");
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("detail", ex.getMessage() != null && !ex.getMessage().equalsIgnoreCase("access denied") ? ex.getMessage() : "Non hai i permessi necessari per effettuare questa operazione");
        body.put("instance", null);
        body.put("properties", null);;
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Object> handleAuthentication(org.springframework.security.core.AuthenticationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "authentication_failed");
        body.put("title", "Authentication Failed");
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("detail", "Credenziali non valide");
        body.put("instance", null);
        body.put("properties", null);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

     @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        String detailMessage = ex.getMostSpecificCause().getMessage();
        body.put("type", "bad_request");
        body.put("title", "Malformed JSON Request");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("instance", null);
        body.put("properties", detailMessage);
        body.put("detail", "Richiesta non leggibile. Controlla il formato dei dati inviati. Dettagli nell'oggetto properties.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private boolean isInvalidRecipientAddress(MailException ex) {
        Throwable current = ex;
        while (current != null) {
            String message = current.getMessage();
            if (message != null) {
                String normalizedMessage = message.toLowerCase();
                if (normalizedMessage.contains("invalid addresses") || normalizedMessage.contains("invalid domain")) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }
}
