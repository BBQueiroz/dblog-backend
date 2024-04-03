package com.dovisen.dblog.domain.user.profile;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileDTO getProfile(UUID uuid){
        Optional<User> user = this.userRepository.findById(uuid);
        ProfileDTO profileDTO = new ProfileDTO(user.get().getNickname(), user.get().getPosts(), user.get().getEmail(), user.get().getBirthday(), user.get().getBiography());
        BeanUtils.copyProperties(user, profileDTO);

        return profileDTO;
    }
    public User updateProfile(UUID id, ProfileDTO profileDTO){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        BeanUtils.copyProperties(profileDTO, existingUser);
        return userRepository.save(existingUser);
    }
}
