package com.ahmed.users.service;

import com.ahmed.users.entities.Role;
import com.ahmed.users.entities.User;
import com.ahmed.users.register.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User findByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String roleName);
    List<User> findAllUsers();
    User registerUser(RegistrationRequest request);
    public void sendEmailUser(User u, String code);

    public User validateToken(String code);



}
