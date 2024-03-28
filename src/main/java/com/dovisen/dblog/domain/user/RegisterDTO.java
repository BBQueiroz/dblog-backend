package com.dovisen.dblog.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}

