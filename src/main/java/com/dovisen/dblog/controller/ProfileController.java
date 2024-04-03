package com.dovisen.dblog.controller;

import com.dovisen.dblog.domain.user.AuthenticationService;
import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.profile.ProfileDTO;
import com.dovisen.dblog.domain.user.UserRepository;
import com.dovisen.dblog.domain.user.profile.ProfileService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {


    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileDetails(@PathVariable(value = "id") UUID id){

        ProfileDTO profileDTO = this.profileService.getProfile(id);

        return  ResponseEntity.status(HttpStatus.OK).body(profileDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable(value = "id") UUID id, @RequestBody ProfileDTO profileDTO){
        User updatedUser = this.profileService.updateProfile(id, profileDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }



}