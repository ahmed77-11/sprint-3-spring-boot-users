package com.ahmed.users.restControllers;

import com.ahmed.users.entities.User;
import com.ahmed.users.register.RegistrationRequest;
import com.ahmed.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest request){
        return userService.registerUser(request);
    }
    @GetMapping("/verifyEmail/{token}")
    public User verifyEmail(@PathVariable("token") String token){
        return userService.validateToken(token);
    }
}
