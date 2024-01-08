package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.Post;
import com.example.springbootrestapi.exception.ResourceNotFoundException;
import com.example.springbootrestapi.payload.PostDto;
import com.example.springbootrestapi.payload.PostResponse;
import com.example.springbootrestapi.repository.PostRepository;
import com.example.springbootrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper mapper;

    @Autowired
    PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post savedPost = postRepository.save(mapToEntity(postDto));
        return mapToDto(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDto> content =  posts.getContent().stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "ID" , String.valueOf(id)));
        return mapToDto(post);
    }

    @Override
    public String deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "ID" , String.valueOf(id)));
        if (post.getId() == id) {
            postRepository.deleteById(id);
        }

        return String.format("Post with ID '%s' is deleted", String.valueOf(id));
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "ID" , String.valueOf(id)));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    private PostDto mapToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
