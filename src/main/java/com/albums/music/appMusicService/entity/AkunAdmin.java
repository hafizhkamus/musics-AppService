package com.albums.music.appMusicService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AkunAdmin {

    private String username;
    private String keyword;
    private Integer groupId;
    private String roleName;
}
