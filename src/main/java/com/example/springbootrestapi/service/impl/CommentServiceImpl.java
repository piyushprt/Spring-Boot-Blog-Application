package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.Comment;
import com.example.springbootrestapi.entity.Post;
import com.example.springbootrestapi.exception.BlogAPIException;
import com.example.springbootrestapi.exception.ResourceNotFoundException;
import com.example.springbootrestapi.payload.CommentDto;
import com.example.springbootrestapi.repository.CommentRepository;
import com.example.springbootrestapi.repository.PostRepository;
import com.example.springbootrestapi.service.CommentService;
import com.example.springbootrestapi.utils.ModelMapperProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository ,ModelMapper modelMapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId ,Long commentId) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment foes not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId ,Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment foes not belong to post");
        }

        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public String deleteComment(Long postId ,Long commentId) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment foes not belong to post");
        }

        commentRepository.delete(comment);

        return String.format("Comment with ID '%s' is deleted", String.valueOf(commentId));
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}
