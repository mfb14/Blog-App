package com.project.questapp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.questapp.business.abstracts.LikeService;
import com.project.questapp.business.abstracts.PostService;
import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.dataAccess.abstracts.PostDao;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.responses.PostResponse;

@Service
public class PostManager implements PostService{

	PostDao postDao;
	UserService userService;
	LikeService likeService;
	
	@Autowired
	public PostManager(PostDao postDao, UserService userService/*, LikeService likeService*/) {
		this.postDao = postDao;
		this.userService = userService;
		//this.likeService = likeService;
	}
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@Override
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> postList;
		
		if(userId.isPresent()) {
			postList = postDao.findByUserId(userId.get());
		}
		postList = postDao.findAll();
		//Bir tane post alıyoruz ve post responsun içerisine gönderiyoruz
		return postList.stream().map(p -> {
		List<LikeResponse> likes = likeService.getAllLikes(Optional.of(null),Optional.of(p.getId()));
		return new PostResponse(p,likes);}).collect(Collectors.toList());
		
	}
	
	@Override
	public Post getOnePostById(Long postId) {
		return postDao.findById(postId).orElse(null);
	}
	/*
	 *- Post create etmek için user objesinin hepsinin gelmesine gerek yok
	 *  Bunun yerine sadece userId gerekli
	 * 
	 *- PostCreateRequest adlı bir sınıf oluşturuyoruz
	 * 
	 * */
	@Override
	public Post createOnePost(PostCreateRequest newPostRequest) {
		//UserService yardımıyla veritabanımızdan user ı çağırıyoruz
		User user = userService.getOneUserById(newPostRequest.getUserId());
		if (user == null)
			return null;
		//Post CreateRequesti objemizi veritabanına kaydedemeyiz
		//Bu yüzden bunu bir Post nesnesine çevirmemiz gerekiyor.
		Post newPost = new Post();
		
		newPost.setText(newPostRequest.getText());
		newPost.setTitle(newPostRequest.getTitle());
		newPost.setUser(user);//Burda veritabanında getirdiğimiz user ı koymamız gerekir.
		return postDao.save(newPost);
		
		
		
	}
	@Override
	public void deleteOnePost(Long postId) {
		postDao.deleteById(postId);
	}
	@Override
	public Post updateOnePostById(Long postId,PostUpdateRequest postUpdateRequest) {
		
		Optional<Post> postOptional = postDao.findById(postId);
		if(postOptional.isPresent()) {
			Post toUpdatePost = postOptional.get();
			toUpdatePost.setText(postUpdateRequest.getText());
			toUpdatePost.setTitle(postUpdateRequest.getTitle());
			postDao.save(toUpdatePost);
			return toUpdatePost;
		}
		return null;
	}

}
