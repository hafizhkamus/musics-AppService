package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.GenreDao;
import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreDao dao;

    public DataTableResponse<Artis> datatables(DataTableRequest res){
        DataTableResponse<Artis> tables = new DataTableResponse<>();
        tables.setData(dao.getAllGenreJson( res));
        Integer total = dao.getBanyakGenre( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }

    public List<Genre> findAll() throws EmptyResultDataAccessException {
        return dao.getAllGenre();
    }

    public Optional<Genre> findById(Integer id) throws EmptyResultDataAccessException {
        return dao.getGenreById(id);
    }

    public void insertOrUpdate(Genre genre) {
        dao.insertOrUpdateGenre(genre);
    }

    public void deleteById(Integer id) throws DataAccessException {
        dao.deleteGenre(id);
    }
}
