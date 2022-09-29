package com.project.questapp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.Post;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;

public interface PostService {

	List<PostResponse> getAllPosts(Optional<Long> userId);

	Post getOnePostById(Long postId);

	Post createOnePost(PostCreateRequest newPostRequest);

	void deleteOnePost(Long postId);

	Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest);

}
