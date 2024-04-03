package com.dovisen.dblog.domain.user.profile;

import com.dovisen.dblog.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;

public record ProfileDTO(String nickname, List<Post> posts, String email, LocalDateTime birthday, String biography) {
}
