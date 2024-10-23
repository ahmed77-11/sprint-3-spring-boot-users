package com.ahmed.users.service;

import com.ahmed.users.entities.Role;
import com.ahmed.users.entities.User;
import com.ahmed.users.entities.VerificationToken;
import com.ahmed.users.exception.EmailAlreadyExistsException;
import com.ahmed.users.exception.ExpiredTokenException;
import com.ahmed.users.exception.InvalidTokenException;
import com.ahmed.users.register.RegistrationRequest;
import com.ahmed.users.repos.RoleRepository;
import com.ahmed.users.repos.UserRepository;
import com.ahmed.users.repos.VerificationTokenRepository;
import com.ahmed.users.util.EmailSender;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    EmailSender emailSender;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(roleName);

        user.getRoles().add(role);
        System.out.println(user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optionalUser=userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        User newUser=new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);
        userRepository.save(newUser);

        Role r=roleRepository.findByRole("USER");
        List<Role> roles=new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);
         userRepository.save(newUser);
         //génére le code du verification
         String code=this.generateCode();
        VerificationToken token=new VerificationToken(code,newUser);
        verificationTokenRepository.save(token);

        //envoyer le code par email
        this.sendEmailUser(newUser,token.getToken());

        return newUser;
    }

    private String generateCode(){
        Random random=new Random();
        Integer code=10000+random.nextInt(90000);
        return code.toString();
    }

    @Override
    public void sendEmailUser(User u, String code) {
        String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" + " Votre code de validation est "+"<h1>"+code+"</h1>";
        emailSender.sendEmail(u.getEmail(), emailBody);
    }


    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepository.findByToken(code);
        if(token == null){
            throw new InvalidTokenException("Invalid Token");
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(token);
            throw new ExpiredTokenException("expired Token");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }

}