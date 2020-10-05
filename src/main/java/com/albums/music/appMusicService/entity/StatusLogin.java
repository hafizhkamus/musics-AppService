package com.albums.music.appMusicService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusLogin {

    private Boolean isValid;
    private String token;
}
