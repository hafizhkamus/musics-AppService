package com.albums.music.appMusicService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artis {

    private Integer idArtis;
    private String namaArtis;
    private MultipartFile file;
    private String foto;
    private String url;
    private String keterangan;
}
