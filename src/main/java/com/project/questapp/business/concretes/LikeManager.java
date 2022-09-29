package com.project.questapp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.questapp.business.abstracts.LikeService;
import com.project.questapp.business.abstracts.PostService;
import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.dataAccess.abstracts.LikeDao;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;

@Service
public class LikeManager implements LikeService {

	LikeDao likeDao;
	PostService postService;
	UserService userService;
	
	public LikeManager(LikeDao likeDao, PostService postService, UserService userService) {
		super();
		this.likeDao = likeDao;
		this.postService = postService;
		this.userService = userService;
	}

	@Override
	public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
		
		List<Like> list;
		if (postId.isPresent()&&userId.isPresent()) {
			
			list =  likeDao.findByUserIdAndPostId(userId.get(),postId.get());	
		
		} else if(userId.isPresent()){
			
			list =  likeDao.findByUserId(userId.get());
		
		}else if(postId.isPresent()) {
			
			list =  likeDao.findByPostId(postId.get());
		
		}else 
			list = likeDao.findAll();
		
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	@Override
	public Like createOneLike(LikeCreateRequest likeCreateRequest) {
		User user = userService.getOneUserById(likeCreateRequest.getUserId());
		Post post = postService.getOnePostById(likeCreateRequest.getPostId());
		
		if(user!=null&&post!=null) {
			
			Like newLike = new Like();
			newLike.setId(likeCreateRequest.getId());
			newLike.setUser(user);
			newLike.setPost(post);
			return likeDao.save(newLike);
		}
		return null;

	}

	@Override
	public void deleteOneLikeById(Long likeId) {
		
		likeDao.deleteById(likeId);
		
	}

	@Override
	public Like getOneLikeById(Long likeId) {
		
		return likeDao.findById(likeId).orElse(null);

	}
	
	
}
