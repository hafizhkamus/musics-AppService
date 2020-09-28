package com.albums.music.appMusicService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lagu {

    private Integer idLagu;
    private String judul;
    private String durasi;
    private Integer idGenre;
    private String namaGenre;
    private Integer idArtis;
    private String namaArtis;
    private Integer idAlbum;
    private String namaAlbums;

}
