package com.blog.services.imple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.UserDto;
import com.blog.repository.RoleRepo;
import com.blog.repository.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImple implements UserService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo; 
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);  // we made object of userDto and we require User object So we create a method which is transfer the object of UserDto to USer...
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id",userId ));
		
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		User updateuser = this.userRepo.save(user);
		
		UserDto userDto1 = this.userToDto(updateuser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));

		this.userRepo.delete(user);
	}

	
	
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		
		
		
		/*
		 * User user= new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 */
		
		
		return user;
		
		
	
	}
	
	public UserDto userToDto(User user) {
		
		
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		 * UserDto userDto = new UserDto(); userDto.setName(user.getName());
		 * userDto.setId(user.getId()); userDto.setEmail(user.getEmail());
		 * userDto.setPassword(user.getPassword()); userDto.setAbout(user.getAbout());
		 */
		
		return userDto;
		
		
	}

	@Override
	public UserDto registernewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword())); 
		
		Role role = this.roleRepo.findById(2).get();
		
		user.getRoles().add(role);
		
		User NewUser = this.userRepo.save(user);
		
		return this.modelMapper.map(NewUser,UserDto.class);
		
	}
	
	
	
	
	
	
}
