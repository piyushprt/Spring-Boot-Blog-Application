package com.example.springbootrestapi.controller;

import com.example.springbootrestapi.payload.CommentDto;
import com.example.springbootrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {

    private CommentService commentService;
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{postId}/comments")
    public ResponseEntity<CommentDto> createComment (@PathVariable Long postId,
                                                     @RequestBody CommentDto commentDto) {
        CommentDto savedComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentByPost (@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PatchMapping("{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") Long postId,
                                                        @PathVariable("id") Long commentId,
                                                        @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateCommentById(postId, commentId,commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") Long postId,
                                                    @PathVariable("id") Long commentId){
        String deleteStatus = commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(deleteStatus, HttpStatus.OK);
    }
 }
