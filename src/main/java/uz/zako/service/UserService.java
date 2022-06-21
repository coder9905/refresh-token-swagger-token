package uz.zako.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import uz.zako.payload.UserPayload;

public interface UserService {

    ResponseEntity<?> saveUser(@RequestBody UserPayload payload);
}
