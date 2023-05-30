package com.blog.services;

import java.util.List;

import com.blog.entity.Post;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {

	// CREATE POST
	
	PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);
	
	// UPDATE POST
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// DELETE POST
	
	void deletePost(Integer postId);
	
	// GET ALL POST
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
	
	// GET POST BY ID
	
	PostDto getPostById(Integer postId);
	
	
	// GET POSTS BY CATEGORY
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	
	// GET POSTS BY USER
	
	List<PostDto> getPostByUser(Integer userId);
	
	
	// SEARCH POST
	
	List<PostDto> serchPost(String keyword);
	
}
