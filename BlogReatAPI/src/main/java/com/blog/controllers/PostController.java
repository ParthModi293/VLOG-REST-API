package com.blog.controllers;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entity.Post;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	
	// CREATE POST
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		
		PostDto createPost = this.postService.createPost(postDto, categoryId, userId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
		
	}
	
	// get Post By User...
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
// GET POST BY CATEGORY
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	// GET ALL POSTS
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required=false )Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required=false )Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required=false )String sortBy

	
			){
		 PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// GET POST BY ID
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
		
	}
	
	// DELETE POST
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
		this.postService.deletePost(postId);
		return new ApiResponse("Post is Deleted!!", true);
		
	}
	
	// UPDATE POST
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	// SEARCH POST
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		
		List<PostDto> posts = this.postService.serchPost(keyword);
		 return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
}
