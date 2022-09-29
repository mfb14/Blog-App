package com.project.questapp.responses;

import com.project.questapp.entities.Comment;


import lombok.Data;

@Data
public class CommentResponse {

	String userName;
	Long postId;
	String postTitle;
	String text;
	
	public CommentResponse(Comment entity) {
		
		userName = entity.getUser().getUserName();
		postId = entity.getPost().getId();
		postTitle = entity.getPost().getTitle();
		text = entity.getText();
	
		
	}
}
