package com.dovisen.dblog.domain.authentication;

import com.dovisen.dblog.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}

