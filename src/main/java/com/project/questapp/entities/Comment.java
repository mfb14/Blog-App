package com.project.questapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="comments")
@Data 
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Post post;
	
	
	@Column(columnDefinition = "text")
	private String text;
	
	
}
