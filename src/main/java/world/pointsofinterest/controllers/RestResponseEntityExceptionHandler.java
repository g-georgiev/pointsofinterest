package world.pointsofinterest.controllers;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import world.pointsofinterest.api.v1.model.ErrorDTO;
import world.pointsofinterest.services.ResourceNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jt on 10/6/17.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){
        ErrorDTO errorDTO = new ErrorDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        List<ErrorDTO> errorDTOS = exception.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String errorFields = "Multiple fields";
                    try{ errorFields = ((FieldError) error).getField(); } catch (Exception ignored) {}
                    return new ErrorDTO(new Date(), errorFields, error.getDefaultMessage());
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorDTOS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
