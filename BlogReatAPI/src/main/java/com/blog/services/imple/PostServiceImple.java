package com.blog.services.imple;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImple implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost = this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost,PostDto.class);
	}

// UPDATE POST
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		Post updatedpost = this.postRepo.save(post);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	// DELETE POST
	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);
		
	}

	// GET ALL POST
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		
		// FOR PAGINATION
		
		
		Pageable p = PageRequest.of(pageNumber, pageSize,org.springframework.data.domain.Sort.by(sortBy).descending());
		
		
		  Page<Post> pagePost = this.postRepo.findAll(p);
		  
		  List<Post> posts = pagePost.getContent();
		
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		// FOR PAGINATION INFORMATION..
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		
		
		
		return postResponse;
	}
	
	// GET SINGLE POST BY ID

	@Override
	public PostDto getPostById(Integer postId) {
		
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		
		return this.modelMapper.map(post, PostDto.class);
	}

	
	
	// GET POST BY CATEGORY
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category  category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> dtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return dtos;
	}

	// GET POST BY USER
	
	
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
User  user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postdtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		
		return postdtos;
	}

	// SEARCH POST
	@Override
	public List<PostDto> serchPost(String keyword) {
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return postDto;
	}

}
