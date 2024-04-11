package com.dovisen.dblog.domain.user;

import com.dovisen.dblog.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name="TB_USERS")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @Column(nullable = false)
    private UserRole role;

    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> following;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;

    @Email
    private String email;

    @DateTimeFormat
    private LocalDateTime birthday;

    @Column
    private String biography;


    public User(String login, String encryptedPassword, UserRole role) {
        this.login = login;
        this.password = encryptedPassword;
        this.role = role;
        this.nickname = login;
        this.biography = "";
        this.email = "";
        this.birthday = LocalDateTime.now();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    @ManyToMany(mappedBy = "likes_list")
    private List<Post> likedPosts = new ArrayList<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getUsername(){
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

