package com.project.questapp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

public interface CommentService {

	List<CommentResponse> getAllComments(Optional<Long> userId,Optional<Long>postId);

	CommentResponse getOneCommentById(Long commentId);

	void deleteOnePost(Long commentId);

	Comment createOneComment(CommentCreateRequest commentCreateRequest);

	Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest);

}
