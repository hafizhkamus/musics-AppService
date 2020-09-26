package com.albums.music.appMusicService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Albums {

    private Integer idAlbum;
    private String namaAlbums;
    private Integer idlabel;
    private Integer idArtis;
    private String fotoCover;
    private String keterangan;
    private String namaArtis;
    private String namaLabels;

}
