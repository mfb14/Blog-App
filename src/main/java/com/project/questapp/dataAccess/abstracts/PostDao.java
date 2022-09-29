package com.project.questapp.dataAccess.abstracts;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.questapp.entities.Post;
public interface PostDao extends JpaRepository<Post, Long> {

	/*
	 * Jpa repository altında o nesne içerisinde yer alan alanlaral findBy...
	 * custom mettotları yazıp birleştirebiliyoruz
	 * */
	 
	List<Post> findByUserId(Long userId);

}
