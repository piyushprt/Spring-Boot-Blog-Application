package com.example.springbootrestapi.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
}
