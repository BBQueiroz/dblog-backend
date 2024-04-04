package com.dovisen.dblog.domain.post;


import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_POST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login")
    @JsonSerialize(using = UserSerializer.class)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Post parent;

    @Column
    private int qtd_likes;

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes_list;

    public Post(PostDTO postDTO){
        this.user = postDTO.user();
        this.title = postDTO.title();
        this.content = postDTO.content();
        this.data = LocalDateTime.now();
        this.qtd_likes = 0;
        this.likes_list = new ArrayList<>();
    }
}
