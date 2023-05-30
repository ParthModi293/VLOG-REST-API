package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.blog.payload.ApiResponse;
import com.blog.payload.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class userController {

	@Autowired
	private UserService userService;
	
	// Post - create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}
	
	
	
	// Put - Update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	
	
	
	// Delete - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		
		this.userService.deleteUser(userId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted user", true), HttpStatus.OK);
		
	
	}
	

	// Get -  get user
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUsers()) ;
	
		
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
		
		return ResponseEntity.ok(this.userService.getUserById(userId)) ;
	
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
