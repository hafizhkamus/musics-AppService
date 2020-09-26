package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.ArtisDao;
import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Artis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtisService {

    @Autowired
    private ArtisDao dao;

    @Value("${files.path}")
    private String basePath;

    public DataTableResponse<Artis> datatables(DataTableRequest res){
        DataTableResponse<Artis> tables = new DataTableResponse<>();
        tables.setData(dao.getAllArtisJson( res));
        Integer total = dao.getBanyakArtis( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }

    public List<Artis> findAll() throws EmptyResultDataAccessException {
        return dao.getAllArtis();
    }

    public Optional<Artis> findById(Integer id) throws EmptyResultDataAccessException {
        return dao.getArtisByID(id);
    }

    public void insertOrUpdate(Artis artis) {
        dao.insertOrUpdateArtis(artis);
    }

    public void deleteById(Integer id) throws DataAccessException {
        dao.deleteArtis(id);
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
