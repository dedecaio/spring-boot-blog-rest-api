package com.caioprogramador.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty( message = "Name must not be empty.")
    private String name;
    @NotEmpty(message = "Email must not be empty.")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters.")
    private String body;
}
