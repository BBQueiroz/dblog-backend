package com.dovisen.dblog.controller;

import com.dovisen.dblog.domain.post.Post;
import com.dovisen.dblog.domain.post.PostDTO;
import com.dovisen.dblog.domain.post.PostService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<Object> savePost(@RequestBody @Valid PostDTO postDTO){
        Post savedPost;
        try{
            savedPost = postService.save(postDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPost);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<Post>> getTopNPosts(@RequestParam(defaultValue = "10") int limit){
        List<Post> posts = postService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePost(@PathVariable(value = "id") UUID id){
        Post post;

        try{
            post = postService.findById(id);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/{parentId}/comments")
    public ResponseEntity<List<Post>> getComments(@PathVariable(value="parentId") UUID parentId){
        List<Post> comments = this.postService.findComments(parentId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Post> changeLike(@PathVariable(value="id") UUID id){
        Post post = this.postService.changeLike(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id){
        try {
            postService.delete(id);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post não encontrado");
    }
}   
