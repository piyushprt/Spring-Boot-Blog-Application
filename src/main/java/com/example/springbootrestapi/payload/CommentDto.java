package com.example.springbootrestapi.payload;

import com.example.springbootrestapi.entity.Post;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
