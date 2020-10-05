package com.albums.music.appMusicService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusLogin {

    private Boolean isValid;
    private String token;
    private List<String> role;
}
