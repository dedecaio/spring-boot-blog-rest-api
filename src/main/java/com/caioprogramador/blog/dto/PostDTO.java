package com.caioprogramador.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDTO Model information"
)
public class PostDTO {
    private Long id;


    @Schema(
            description = "Blog Post title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title must have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description must have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
    private Long categoryId;
}
