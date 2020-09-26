package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.AlbumsDao;
import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Artis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumsService {

    @Autowired
    private AlbumsDao dao;

    @Value("${files.path.albums}")
    private String basePath;

    public DataTableResponse<Albums> datatables(DataTableRequest res){
        DataTableResponse<Albums> tables = new DataTableResponse<>();
        tables.setData(dao.getAllAlbumsRec( res));
        Integer total = dao.getBanyakAlbums( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }

    public List<Albums> findAll() throws EmptyResultDataAccessException {
        return dao.getAllAlbums();
    }

    public List<Albums> findByLabels(Integer idLabel) throws EmptyResultDataAccessException{
        return dao.getAlbumsByLabels(idLabel);
    }

    public List<Albums> findByArtis(Integer id) throws EmptyResultDataAccessException{
        return dao.getAlbumsByArtist(id);
    }

    public Optional<Albums> findById(Integer id) throws EmptyResultDataAccessException {
        return dao.getAlbumsById(id);
    }

    public void insertOrUpdate(Albums albums) {
        dao.insertOrUpdateAlbums(albums);
    }

    public void deleteById(Integer id) throws DataAccessException {
        dao.deleteAlbums(id);
    }

    public String saveFile(MultipartFile file){
        try{
            Path root = Paths.get(basePath);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length - 1];
            String uuid = UUID.randomUUID().toString() + "." + extension;
            Files.copy(file.getInputStream(), root.resolve(uuid));
            return uuid;
        } catch (IOException e){
            throw new RuntimeException("could not store the file. error : " + e.getMessage());
        }
    }
}
