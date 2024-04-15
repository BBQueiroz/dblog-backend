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

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post save(PostDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = new Post(postDTO);
        post.setUser((User) userRepository.findByLogin(authentication.getName()));
        if(postDTO.parent() != null) {
            post.setParent(postRepository.findById(postDTO.parent()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND)));
        }
        return postRepository.save(post);
    }

    public Boolean existsByTitle(String title){
        return postRepository.existsByTitle(title);
    }

    public List<Post> findAll() { return postRepository.findAll().stream().filter(post -> post.getParent() == null).toList(); }

    public List<Post> findTopNPost(int limit){
        Pageable pageable = (Pageable) PageRequest.of(0, limit);
        return postRepository.findTopNByData(pageable).stream().filter( post -> post.getParent() == null).toList();
    }

    public List<Post> findComments(UUID id){
        return postRepository.findAll().stream().filter(post -> post.getParent() != null && post.getParent() == postRepository.findById(id).get()).toList();
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
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        List<User> listUser = post.getLikeList();
        if(post.getQtdLikes() == null){
            post.setQtdLikes(0);
        }
        User usuarioEncontrado = (User) userRepository.findByLogin(authentication.getName());

        if(listUser.contains(usuarioEncontrado)){
            post.setQtdLikes(post.getQtdLikes() - 1);
            listUser.remove(usuarioEncontrado);

            return post;
        }
        post.setQtdLikes(post.getQtdLikes() + 1);
        listUser.add((User) userRepository.findByLogin(authentication.getName()));

        return post;
    }
    public Post addLike(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        post.setQtdLikes(post.getQtdLikes() + 1);
        List<User> listUser = post.getLikeList();
        listUser.add((User) userRepository.findByLogin(authentication.getName()));
        post.setLikeList(listUser);

        return post;
    }
    public Post removeLike(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuarioEncontrado = (User) userRepository.findByLogin(authentication.getName());

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        post.setQtdLikes(post.getQtdLikes() - 1);

        List<User> listUser = post.getLikeList();
        listUser.remove(usuarioEncontrado);

        post.setLikeList(listUser);

        return post;
    }


    public Post findById(UUID id) {
        if (postRepository.findById(id).isEmpty()){
            throw new NullPointerException("Não encontrado");
        }
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
    }
}
