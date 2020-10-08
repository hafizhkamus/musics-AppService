package com.albums.music.appMusicService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class New {
        private String username;
        private String keyword;
        private Integer groupId;
        private Timestamp tglRegister;
    }
}
