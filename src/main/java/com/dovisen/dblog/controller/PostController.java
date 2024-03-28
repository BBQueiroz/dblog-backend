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
        return ResponseEntity.status(HttpStatus.CREATED).body("201 Created");
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePlaylist(@PathVariable(value = "id") UUID id){
        Post post = new Post();

        try{
            post = postService.findById(id);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePlaylist(@PathVariable(value = "id") UUID id){
        try {
            postService.delete(id);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("204 No Content");
    }
}   
