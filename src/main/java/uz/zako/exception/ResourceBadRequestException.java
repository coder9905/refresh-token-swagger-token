package uz.zako.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {

    private String message;

    public ResourceBadRequestException(String message) {
        super(message);
    }

}
