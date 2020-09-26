package com.albums.music.appMusicService.dataTables;

import lombok.Data;

import java.util.Map;

@Data
public class DataTableRequest {

    private Integer draw, start, length, sortCol;
    private Map<String, Object> extraParam;
    private String sortDir;

}
