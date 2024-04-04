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
        if(postDTO.parent() != null) {
            post.setParent(postRepository.findById(postDTO.parent()).get());
        }
        return postRepository.save(post);
    }

    public Boolean existsByTitle(String title){
        return postRepository.existsByTitle(title);
    }

    public List<Post> findAll() { return postRepository.findAll().stream().filter(post -> post.getParent() == null).collect(Collectors.toList()); }

    public List<Post> findTopNPost(int limit){
        Pageable pageable = (Pageable) PageRequest.of(0, limit);
        return postRepository.findTopNByData(pageable).stream().filter( post -> post.getParent() == null).collect(Collectors.toList());
    }

    public List<Post> findComments(UUID id){
        return postRepository.findAll().stream().filter(post -> post.getParent() != null && post.getParent() == postRepository.findById(id).get()).collect(Collectors.toList());
    }
    public void delete(UUID uuid){
        if(!postRepository.existsById(uuid)) throw new NullPointerException("404 Not Found");
        postRepository.deleteById(uuid);
    }

    public Boolean existsById(UUID id){
        return postRepository.existsById(id);
    }
    public Post changeLike(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        List<User> listUser = post.getLikes_list();
        if(post.getQtd_likes() == null){
            post.setQtd_likes(0);
        }
        if(listUser.contains((User) userRepository.findByLogin(authentication.getName()))){
            post.setQtd_likes(post.getQtd_likes() - 1);
            listUser.remove((User) userRepository.findByLogin(authentication.getName()));

            return post;
        }
        post.setQtd_likes(post.getQtd_likes() + 1);
        listUser.add((User) userRepository.findByLogin(authentication.getName()));

        return post;
    }
    public Post addLike(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        post.setQtd_likes(post.getQtd_likes() + 1);
        List<User> listUser = post.getLikes_list();
        listUser.add((User) userRepository.findByLogin(authentication.getName()));
        post.setLikes_list(listUser);

        return post;
    }
    public Post removeLike(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        post.setQtd_likes(post.getQtd_likes() - 1);
        List<User> listUser = post.getLikes_list();
        listUser.remove((User) userRepository.findByLogin(authentication.getName()));
        post.setLikes_list(listUser);

        return post;
    }


    public Post findById(UUID id) {
        if (postRepository.findById(id).isEmpty()){
            throw new NullPointerException("Não encontrado");
        }
        return postRepository.findById(id).get();
    }
}
