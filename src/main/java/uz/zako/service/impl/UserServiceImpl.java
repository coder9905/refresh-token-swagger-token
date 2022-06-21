package uz.zako.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.zako.entity.RefreshToken;
import uz.zako.entity.User;
import uz.zako.model.Result;
import uz.zako.payload.UserPayload;
import uz.zako.repository.RefreshTokenRepository;
import uz.zako.repository.RoleRepository;
import uz.zako.repository.UserRepository;
import uz.zako.service.UserService;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public ResponseEntity<?> saveUser(@RequestBody UserPayload payload){
        try {
            User user = new User();
            user.setUsername(payload.getUsername());
            user.setFullName(payload.getFullName());
            user.setPassword(bCryptPasswordEncoder.encode(payload.getPassword()));
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            user.setIsAdmin(payload.getIsAdmin());

            user=userRepository.save(user);

            if (user != null){
                return ResponseEntity.ok(new Result(true,"save user succesfull",user));
            }
            return new ResponseEntity(new Result(false,"save error user",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            log.error("user save error",e.getMessage());
            return new ResponseEntity(new Result(false,"save error user",null), HttpStatus.BAD_REQUEST);
        }
    }




}
