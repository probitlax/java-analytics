package com.example.analytics.exception;

public class ProfileNotFoundException extends RuntimeException {

    public static final String PROFILE_NOT_FOUND = "profile is not found";
    public ProfileNotFoundException() {
        super(PROFILE_NOT_FOUND);
    }
}
