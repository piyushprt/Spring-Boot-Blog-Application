package com.example.springbootrestapi.controller;

import com.example.springbootrestapi.entity.Post;
import com.example.springbootrestapi.payload.PostDto;
import com.example.springbootrestapi.payload.PostResponse;
import com.example.springbootrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost (@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost (
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<PostDto> getPostById(@RequestParam long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable long id, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePostById(id, postDto), HttpStatus.OK);
    }
}
