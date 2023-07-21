package com.caioprogramador.blog.service;

import com.caioprogramador.blog.dto.PostDTO;
import com.caioprogramador.blog.dto.PostResponseDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO,Long id);

    void deletePost(Long id);

    List<PostDTO> getPostsByCategory(Long categoryId);
}
