package com.ahmed.users;

import com.ahmed.users.entities.Role;
import com.ahmed.users.entities.User;
import com.ahmed.users.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersMicroseviceApplication {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UsersMicroseviceApplication.class, args);
	}


//	@PostConstruct
//	void init_users() {
//		//ajouter les rôles
//		userService.addRole(new Role(null, "ADMIN"));
//		userService.addRole(new Role(null, "USER"));
//		//ajouter les users
//		userService.saveUser(new User(null, "admin", "123", true, null));
//		userService.saveUser(new User(null, "ahmed", "123", true, null));
//		userService.saveUser(new User(null, "dexter", "123", true, null));
//		//ajouter les rôles aux users
//		userService.addRoleToUser("admin", "ADMIN");
//		userService.addRoleToUser("admin", "USER");
//		userService.addRoleToUser("ahmed", "USER");
//		userService.addRoleToUser("dexter", "USER");
//
//	}





}
