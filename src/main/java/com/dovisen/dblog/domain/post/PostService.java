package com.dovisen.dblog.domain.post;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.dovisen.dblog.domain.post.PostRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post save(PostDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = new Post(postDTO);
        post.setUser((User) userRepository.findByLogin(authentication.getName()));
        return postRepository.save(post);
    }

    public Boolean existsByTitle(String title){
        return postRepository.existsByTitle(title);
    }

    public List<Post> findAll() { return postRepository.findAll().stream().filter( post -> post.getParent() == null).collect(Collectors.toList()); }

    public List<Post> findTopNPost(int limit){
        Pageable pageable = (Pageable) PageRequest.of(0, limit);
        return postRepository.findTopNByData(pageable).stream().filter( post -> post.getParent() == null).collect(Collectors.toList());
    }

    public List<Post> findComments(UUID id){
        return postRepository.findByParentId(id);
    }
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
