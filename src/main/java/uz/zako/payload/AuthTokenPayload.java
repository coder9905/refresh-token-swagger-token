package uz.zako.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthTokenPayload {

    private String access_token;
    private String refresh_token;
    private String username;
//    private String token_type;
    private boolean succes;

}
