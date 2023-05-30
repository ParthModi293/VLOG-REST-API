package com.blog;

import java.util.*;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.blog.entity.Role;
import com.blog.repository.RoleRepo;



@SpringBootApplication
public class BlogReatApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogReatApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abc"));

		try {

			Role role1 = new Role();
			role1.setId(1);
			role1.setName("ROLE_ADMIN");
			

			Role role2 = new Role();
			role2.setId(2);
			role2.setName("ROLE_NORMAL");
	
			List<Role> roles = List.of(role1, role2);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
		
			result.forEach(r-> {
				
				System.out.println(r.getName());
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
