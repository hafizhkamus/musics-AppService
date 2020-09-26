package com.albums.music.appMusicService.dataTables;

import lombok.Data;

import java.util.List;

@Data
public class DataTableResponse<T> {

    private List<T> data;
    private Integer draw;
    private Integer recordFiltered, recordsTotal;
}
