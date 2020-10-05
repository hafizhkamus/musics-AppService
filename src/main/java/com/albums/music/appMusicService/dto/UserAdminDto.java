package com.albums.music.appMusicService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserAdminDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Username{
        private String userName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Password{
        private String userPassword;
    }

}
