package com.dovisen.dblog.domain.Journey;

import com.dovisen.dblog.domain.user.User;

import java.util.List;
import java.util.UUID;

public record JourneyDTO(UUID id, JourneyNode root, String text, User user, List<JourneyNode> children, JourneyNode parent) {
}
