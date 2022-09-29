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
@Table(name="posts")
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	//@Type(type = "org.hibernate.type.TextType")  
	@Column(name = "text" ,columnDefinition = "TEXT",length = 500)
	private String text;
	
	@Column(name = "title",length = 25)
	private String title;
	/*
	 * .LAZY Post ve User bağlantılı sınıf olduğu için Post objesini çektiğimde
	 * ilgili user objesini getirmene gerek yok demiş oluyoruz
	 */
	@ManyToOne(fetch=FetchType.EAGER)//Birçok postu bir user atmış olabilir
	@JoinColumn(name = "user_id",nullable = false)//user_id ile user tablosuna bağlandığımızı gösteriyoruz
	@OnDelete(action = OnDeleteAction.CASCADE)//Bir user sildiğmizde onun bütün postlarını silmek için
	User user;
	
	
}
