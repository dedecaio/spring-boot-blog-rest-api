package com.caioprogramador.blog.service.impl;

import com.caioprogramador.blog.dto.CategoryDTO;
import com.caioprogramador.blog.dto.CommentDTO;
import com.caioprogramador.blog.dto.PostDTO;
import com.caioprogramador.blog.dto.PostResponseDTO;
import com.caioprogramador.blog.entity.Category;
import com.caioprogramador.blog.entity.Comment;
import com.caioprogramador.blog.entity.Post;
import com.caioprogramador.blog.exception.ResourceNotFoundException;
import com.caioprogramador.blog.repository.CategoryRepository;
import com.caioprogramador.blog.repository.PostRepository;
import com.caioprogramador.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                        String.format("%d", postDTO.getCategoryId())));
        Post post = mapToEntity(postDTO);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post : posts){
            PostDTO postDTO = mapToDTO(post);
            postDTOS.add(postDTO);
        }
        PostResponseDTO postResponseDTO = new PostResponseDTO(postDTOS, posts.getNumber(), posts.getSize(),
                posts.getTotalElements(), posts.getTotalPages(), posts.isLast(), posts.getSort().toString());
        return postResponseDTO;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.format("%d", id)));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.format("%d", id)));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                        String.format("%d", postDTO.getCategoryId())));
        post.setCategory(category);



        Post updatePost = postRepository.save(post);

        return mapToDTO(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",String.format("%d", id)));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", String.format("%d", categoryId)));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
    }


    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        Set<Comment> comments = post.getComments();
        Set<CommentDTO> commentsDTO = new HashSet<>();
        for(Comment comment:comments){
            CommentDTO commentDTO = CommentServiceImpl.mapToDTO(comment);
            commentsDTO.add(commentDTO);
        }
        Category category = post.getCategory();
        postDTO.setCategoryId(category.getId());
        postDTO.setComments(commentsDTO);
        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Set<CommentDTO> commentsDTO = postDTO.getComments();
        Set<Comment> comments = new HashSet<>();
        for(CommentDTO commentDTO:commentsDTO){
            Comment comment = CommentServiceImpl.mapToEntity(commentDTO);
            comments.add(comment);
        }
        post.setComments(comments);
        return post;
    }
}
