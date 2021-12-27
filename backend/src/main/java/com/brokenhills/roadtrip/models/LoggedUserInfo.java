package com.brokenhills.roadtrip.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoggedUserInfo {

    private String username;
    private String role;
    private boolean isNonLocked;
    private boolean isNonExpired;
    private boolean isEnabled;
    private boolean isCredentialsNonExpired;
}
