package com.blog.services.imple;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepo;
import com.blog.repository.PostRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceImple implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
	
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedcomment = this.commentRepo.save(comment);
		
		
		return this.modelMapper.map(savedcomment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commmentId) {
		
		Comment comment = this.commentRepo.findById(commmentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commmentId));
		this.commentRepo.delete(comment);
	
	}

}
