package com.project.questapp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;

public interface LikeService {

	List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId);

	Like createOneLike(LikeCreateRequest likeCreateRequest);

	void deleteOneLikeById(Long likeId);

	Like getOneLikeById(Long likeId);

}
