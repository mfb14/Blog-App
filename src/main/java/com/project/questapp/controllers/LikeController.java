package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.abstracts.LikeService;
import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;

@RestController
@RequestMapping("/likes")
public class LikeController {

	@Autowired
	LikeService likeService;
	
	@GetMapping
	public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
		return likeService.getAllLikes(userId,postId);
	}
	
	@GetMapping("/{likeId}")
	public Like getOneLike(@PathVariable Long likeId) {
		return likeService.getOneLikeById(likeId);
	}
	
	@PostMapping
	public Like createOneLike(@RequestBody LikeCreateRequest likeCreateRequest) {
		
		return likeService.createOneLike(likeCreateRequest);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteOneLike(Long likeId) {
		likeService.deleteOneLikeById(likeId);
	}
}
