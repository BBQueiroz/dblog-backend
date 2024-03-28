package com.dovisen.dblog.domain.post;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post save(PostDTO postDTO){
        if(existsByTitle(postDTO.title())) throw new IllegalArgumentException("Já existe post com este titulo");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = new Post(postDTO);
        post.setUser((User) userRepository.findByLogin(authentication.getName()));
        return postRepository.save(post);
    }

    public Boolean existsByTitle(String title){
        return postRepository.existsByTitle(title);
    }

    public List<Post> findAll() { return postRepository.findAll(); }

    public void delete(UUID uuid){
        if(!postRepository.existsById(uuid)) throw new NullPointerException("404 Not Found");
        postRepository.deleteById(uuid);
    }

    public Boolean existsById(UUID id){
        return postRepository.existsById(id);
    }

    public Post findById(UUID id) {
        if(!existsById(id)) throw new NullPointerException("404 Not Found");
        return postRepository.findById(id).get(); }
}
