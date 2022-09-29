package com.project.questapp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.questapp.business.abstracts.CommentService;
import com.project.questapp.business.abstracts.PostService;
import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.dataAccess.abstracts.CommentDao;
import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@Service
public class CommentManager implements CommentService{
	
	@Autowired
	CommentDao commentDao;
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	
	
	@Override
	public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
	
		List<Comment> commentList;
		
		if(userId.isPresent() && postId.isPresent()) {
			commentList =  commentDao.findByUserIdAndPostId(userId.get(),postId.get());
		}else if (userId.isPresent()) {
			commentList =  commentDao.findByUserId(userId.get());
		}else if (postId.isPresent()) {
			commentList =  commentDao.findByPostId(postId.get());
		}else {
			commentList =  commentDao.findAll();
		}
		return commentList.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
		

	}
	@Override
	public Comment createOneComment(CommentCreateRequest commentCreateRequest) {
		
		Post post = postService.getOnePostById(commentCreateRequest.getPostId());
		User user = userService.getOneUserById(commentCreateRequest.getUserId());
		
		if (user != null && post != null) {
			
			Comment newComment = new Comment();
			
			newComment.setId(commentCreateRequest.getId());
			newComment.setPost(post);
			newComment.setUser(user);
			newComment.setText(commentCreateRequest.getText());
			
			return commentDao.save(newComment);
			
		} 
		else 
			return null;
		
	}


	@Override
	public CommentResponse getOneCommentById(@PathVariable Long commentId) {
		Comment comment;
		
		comment =  commentDao.findById(commentId).orElse(null);
		
		return new CommentResponse(comment);
		
		
		
		
		
	
	}



	@Override
	public void deleteOnePost(Long commentId) {
		
		commentDao.deleteById(commentId);

		
	}
	@Override
	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
		
		Optional<Comment> commentOptional = commentDao.findById(commentId);
		if (commentOptional.isPresent()) {
			
			Comment toUpdateComment = commentOptional.get();
			toUpdateComment.setText(commentUpdateRequest.getText());
			commentDao.save(toUpdateComment);
			return toUpdateComment;
		} else {
			return null;
		}

	}



	

}
