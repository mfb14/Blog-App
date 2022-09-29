package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.abstracts.CommentService;
import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@RequestMapping("/comments")
@RestController
public class CommentController {

	@Autowired
	CommentService commentService;
	
	
	@GetMapping
	public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
		return commentService.getAllComments(userId,postId);
		
	}
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest commentCreateRequest) {
		return commentService.createOneComment(commentCreateRequest);
	}
	
	@GetMapping("/{commentId}")
	public CommentResponse getOneComment(@PathVariable Long commentId) {
		
		return commentService.getOneCommentById(commentId);
		
	}
	@PutMapping("/{commentId}")
	public Comment updateOneComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest) {
		return commentService.updateOneCommentById(commentId,commentUpdateRequest);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteOneComment(@PathVariable Long commentId) {
		
		commentService.deleteOnePost(commentId);
	}
	
}
