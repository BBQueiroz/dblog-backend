package com.dovisen.dblog.controller;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.profile.ProfileDTO;
import com.dovisen.dblog.domain.user.profile.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {


    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<ProfileDTO> getProfileDetails(@PathVariable(value = "login") String login){

        ProfileDTO profileDTO = this.profileService.getProfile(login);

        return  ResponseEntity.status(HttpStatus.OK).body(profileDTO);
    }

    @PutMapping("/{login}")
    public ResponseEntity<User> updateProfile(@PathVariable(value = "login") String login, @RequestBody ProfileDTO profileDTO){
        User updatedUser = this.profileService.updateProfile(login, profileDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }



}