package com.albums.music.appMusicService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LablesRekaman {

    private Integer idLabel;
    private String namaLabels;
    private String alamat;
    private String noTelp;
    private String contactPerson;
    private String url;
}
