package com.dovisen.dblog.domain.user.profile;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileDTO getProfile(String login){
        User user = (User) this.userRepository.findByLogin(login);

        return new ProfileDTO(user.getNickname(), user.getPosts(), user.getEmail(), user.getBirthday(), user.getBiography());
    }
    public User updateProfile(String login, ProfileDTO profileDTO){
        User existingUser = (User) userRepository.findByLogin(login);

        BeanUtils.copyProperties(profileDTO, existingUser);
        return userRepository.save(existingUser);
    }
}
