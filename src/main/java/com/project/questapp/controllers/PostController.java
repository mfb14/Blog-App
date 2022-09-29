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

import com.project.questapp.business.abstracts.PostService;
import com.project.questapp.entities.Post;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	@Autowired
	private PostService postService;
	
	/*
	 * RequestParam->Bize gelen Reequestin içerisindeki parametreleri pars et ve
	 * sağında bulunan değişkenin içerisine at
	 * Bu bi optional parametre olduğu için geldiği durumda userId ye göre postları getireceğiz
	 */	
	
	@GetMapping()
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
		return postService.getAllPosts(userId);
	}
	 
	 @GetMapping("/{postId}")
	 public Post getOnePost(@PathVariable Long postId) {
		 return postService.getOnePostById(postId);
	 }
	 
	 @PostMapping
	 public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		 return postService.createOnePost(newPostRequest);
		 
	 }
	 @PutMapping("/{postId}")
	 public Post updateOnePostById(@PathVariable Long postId,@RequestBody PostUpdateRequest postUpdateRequest) {
		 return postService.updateOnePostById(postId,postUpdateRequest);
	 }
	 
	 @DeleteMapping
	 public void deleteOnePost(@PathVariable Long postId) {
		 postService.deleteOnePost(postId);
	 }
	
}
