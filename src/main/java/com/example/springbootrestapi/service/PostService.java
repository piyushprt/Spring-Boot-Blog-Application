package com.example.springbootrestapi.service;

import com.example.springbootrestapi.payload.PostDto;
import com.example.springbootrestapi.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    String deletePostById(long id);

    PostDto updatePostById(long id, PostDto postDto);

}
