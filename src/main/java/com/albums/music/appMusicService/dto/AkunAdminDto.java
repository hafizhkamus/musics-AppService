package com.albums.music.appMusicService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AkunAdminDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Information{
        private String username;
        private String keyword;
        private Integer groupId;
        private String roleName;
    }
}
