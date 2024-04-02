package com.dovisen.dblog.domain.post;

import com.dovisen.dblog.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDTO(UUID id, User user, String title, String content, List<LocalDateTime[]> histPost, Post parent) {
}
