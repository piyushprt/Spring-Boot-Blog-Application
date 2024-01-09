package com.example.springbootrestapi.service;

import com.example.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);
    String deleteComment (Long postId, Long commentId);
}
