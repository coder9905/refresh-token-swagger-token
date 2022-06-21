package uz.zako.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.zako.entity.User;
import uz.zako.exception.ResourceNotFoundException;
import uz.zako.repository.UserRepository;
import uz.zako.security.SecurityUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser() {
        User user=userRepository.findByUsername(SecurityUtils.getCurrentUser().orElseThrow(() -> new ResourceNotFoundException("user not found")));
        System.out.println(user);
        return user;
    }

}