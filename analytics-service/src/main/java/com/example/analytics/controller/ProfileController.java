package com.example.analytics.controller;

import com.example.analytics.entity.Profile;
import com.example.analytics.model.ProfileDto;
import com.example.analytics.service.ProfileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/create-profile")
    public ResponseEntity<Void> saveProfile(@RequestBody ProfileDto profileDto, HttpServletResponse response) {
        Profile profile = profileService.saveProfile(profileDto);
        response.setHeader("Profile-Id", String.valueOf(profile.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestHeader("profile-Id") Integer profileId, @RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(profileService.updateProfile(profileId, profileDto));
    }

    @GetMapping("/find-profile-by-id/{profileId}")
    public ResponseEntity<ProfileDto> findProfileById(@PathVariable("profileId") Integer profileId) {
        return ResponseEntity.ok(profileService.findProfileById(profileId));
    }
}
