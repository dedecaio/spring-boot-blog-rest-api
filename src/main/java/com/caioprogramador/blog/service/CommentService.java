package com.caioprogramador.blog.service;

import com.caioprogramador.blog.dto.CommentDTO;

import java.util.List;
public interface CommentService {
    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO getCommentById(Long postId, Long id);

    CommentDTO updateComment(CommentDTO commentDTO,Long postId, Long id);

    void deleteComment(Long postId, Long id);
}
