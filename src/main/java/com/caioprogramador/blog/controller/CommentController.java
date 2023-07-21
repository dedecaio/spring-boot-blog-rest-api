package com.caioprogramador.blog.controller;

import com.caioprogramador.blog.dto.CommentDTO;
import com.caioprogramador.blog.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO newCommentDTO = commentService.createComment(postId, commentDTO);

        return new ResponseEntity<>(newCommentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getAllComents(@PathVariable Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/{postId}/comments/{id}")
    public CommentDTO getCommentById(@PathVariable Long postId, @PathVariable Long id){
        return commentService.getCommentById(postId, id);
    }
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentById(@Valid @RequestBody CommentDTO commentDTO,@PathVariable Long postId, @PathVariable Long id){
        CommentDTO updateCommentDTO = commentService.updateComment(commentDTO,postId,id);

        return new ResponseEntity<>(updateCommentDTO, HttpStatus.OK);
    }
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long postId, @PathVariable Long id){
        commentService.deleteComment(postId,id);

        return new ResponseEntity<>("Comment where post_id is "+postId+" and id is "+id+" was deleted", HttpStatus.OK);
    }
}
