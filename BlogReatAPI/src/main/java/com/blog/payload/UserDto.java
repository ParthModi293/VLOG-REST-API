package com.blog.payload;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.internal.bytebuddy.asm.Advice.Enter;

import com.blog.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotBlank(message = "name should not be null")
	private String name;
	
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "invalid email")
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String about;
	
	
	private Set<RoleDto> roles = new HashSet<>();
	
}
 