package com.project.questapp.responses;

import java.util.List;


import com.project.questapp.entities.Post;

import lombok.Data;

@Data
public class PostResponse {

	Long id;
	Long userId;
	String userName;
	String title;
	String text;
	List<LikeResponse> postLİkes;
	
	public PostResponse(Post entity,List<LikeResponse> postLikes) {
		this.postLİkes=postLikes;
		id = entity.getId();
		userId = entity.getUser().getId();
		userName = entity.getUser().getUserName();
		title = entity.getTitle();
		text = entity.getText();
	}
}
