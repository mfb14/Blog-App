package com.project.questapp.requests;

import lombok.Data;

/*
 * 
 * */
@Data
public class PostCreateRequest {

	
	private String text;
	private String title;
	private Long userId;
}
