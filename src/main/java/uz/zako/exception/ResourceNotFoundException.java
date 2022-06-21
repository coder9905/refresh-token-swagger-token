package uz.zako.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotFoundException extends RuntimeException {

    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
