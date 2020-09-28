package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.LaguDao;
import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Lagu;
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
public class LaguService {

    @Autowired
    private LaguDao dao;

    @Value("${files.path.lagu}")
    private String basePath;

    public DataTableResponse<Lagu> datatables(DataTableRequest res){
        DataTableResponse<Lagu> tables = new DataTableResponse<>();
        tables.setData(dao.getAllLaguRec( res));
        Integer total = dao.getBanyakLagu( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }

    public List<Lagu> findAll() throws EmptyResultDataAccessException {
        return dao.getAllLagu();
    }

    public List<Lagu> findByGenre(Integer idGenre) throws EmptyResultDataAccessException{
        return dao.getLaguByGenre(idGenre);
    }

    public List<Lagu> findByAlbums(Integer idAlbum) throws EmptyResultDataAccessException{
        return dao.getLaguByAlbums(idAlbum);
    }

    public List<Lagu> findByArtis(Integer id) throws EmptyResultDataAccessException{
        return dao.getLaguByArtis(id);
    }

    public Optional<Lagu> findById(Integer id) throws EmptyResultDataAccessException {
        return dao.getLaguById(id);
    }

    public void insertOrUpdate(Lagu lagu) {
        dao.insertOrUpdateLagu(lagu);
    }

    public void deleteById(Integer id) throws DataAccessException {
        dao.deleteLagu(id);
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
